> 以项目驱动学习，以实践检验真知

# 前言

我在上一篇博客中写了如何通过参数校验 + 统一响应码 + 统一异常处理来构建一个优雅后端接口体系：

[【项目实践】SpringBoot三招组合拳，手把手教你打出优雅的后端接口](https://github.com/RudeCrab/rude-java/blob/master/project-practice/validation-and-exception-handler/validation-and-exception-handler.md)。我们做到了：

- 通过Validator + 自动抛出异常来完成了方便的参数校验
- 通过全局异常处理 + 自定义异常完成了异常操作的规范
- 通过数据统一响应完成了响应数据的规范
- 多个方面组装非常优雅的完成了后端接口的协调，让开发人员有更多的经历注重业务逻辑代码，轻松构建后端接口

这样看上去好像挺完美的，很多地方做到了统一和规范。但！事物往往是一体两面的，统一和规范带来的好处自然不必多说，那坏处呢？坏处就是**不够灵活**。

# 数据统一响应

不够灵活主要体现在哪呢，就是数据统一响应这一块。后端响应给前端的数据一共分为三个部分：

code：响应码，比如1000代表响应成功，1001代表响应失败等等

msg：响应信息，用来说明/描述响应情况

data：响应的具体数据

我们通过响应码枚举做到了code和msg的统一，无论怎样我们只会响应枚举规定好的code和msg。我天真的以为这样就能满足所有应用场景了，直到我碰到了一位网友的提问：

> 想请问下如果我检验的每个参数对应不同的错误信息，即code，message都不同 这样该如何处理呢？因为这些错误码是有业务含义的，比如说手机号校验的错误码是V00001，身份证号错误码是V00002。

这一下把我问的有点懵，当时回答道validation参数校验失败的话可以手动捕捉参数校验异常对象，判断是哪个字段，再根据字段手动返回错误代码。我先来演示一下我所说的这种极为麻烦的做法：

## 手动捕捉异常对象

因为BindingResult对象里封装了很多信息，我们可以拿到校验错误的字段名，拿到了字段名后再响应对应的错误码和错误信息。在Controller层里对BindingResult进行了处理自然就不会被我们之前写的全局异常处理给捕获到，也就不会响应那统一的错误码了，从而达到了每个字段有自己的响应码和响应信息：

```java
@PostMapping("/addUser")
public ResultVO<String> addUser(@RequestBody @Valid User user, BindingResult bindingResult) {
    for (ObjectError error : bindingResult.getAllErrors()) {
        // 拿到校验错误的参数字段
        String field = bindingResult.getFieldError().getField();
        // 判断是哪个字段发生了错误，然后返回数据响应体
        switch (field) {
            case "account":
                return new ResultVO<>(100001, "账号验证错误", error.getDefaultMessage());
            case "password":
                return new ResultVO<>(100002, "密码验证错误", error.getDefaultMessage());
            case "email":
                return new ResultVO<>(100003, "邮箱验证错误", error.getDefaultMessage());
        }
    }
    // 没有错误则返回则直接返回正确的信息
    return new ResultVO<>(userService.addUser(user));
}
```

我们故意输错参数，来看下效果：

![](https://rudecrab-image-hosting.oss-cn-shenzhen.aliyuncs.com/blog/20200428231039.png)



嗯，是达到效果了。不过这代码一放出来简直就让人头疼不已。繁琐、维护性差、复用性差，这才判断三个字段就这样子了，要那些特别多字段的还不得起飞咯？

这种方式直接pass！

那我们不手动捕捉异常，我们直接舍弃validation校验，手动校验呢？

## 手动校验

我们来试试：

```java
@PostMapping("/addUser")
public ResultVO<String> addUser(@RequestBody User user) {
    // 参数校验
    if (user.getAccount().length() < 6 || user.getAccount().length() > 11) {
        return new ResultVO<>(100001, "账号验证错误", "账号长度必须是6-11个字符");
    }
    if (user.getPassword().length() < 6 || user.getPassword().length() > 16) {
        return new ResultVO<>(100002, "密码验证错误", "密码长度必须是6-16个字符");
    }
    if (!Pattern.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", user.getEmail())) {
        return new ResultVO<>(100003, "邮箱验证错误", "邮箱格式不正确");
    }
    // 没有错误则返回则直接返回正确的信息
    return new ResultVO<>(userService.addUser(user));
}
```

我去，这还不如上面那种方式呢。上面那种方式至少还能享受validation校验规则的便利性，这种方式简直又臭又长。

那有什么办法既享受validation的校验规则，又能做到为每个字段制定响应码呢？不卖关子了，当然是有滴嘛！

还记得我们前面所说的BindingResult可以拿到校验错误的字段名吗？既然可以拿到字段名，我们再进一步当然也可以拿到字段Field对象，能够拿到Field对象我们也能同时拿到字段的注解嘛。对，咱们就是要用注解来优雅的实现上面的功能！

## 自定义注解

如果validation校验失败了，我们可以拿到字段对象并能够获取字段的注解信息，那么只要我们为每个字段带上注解，注解中带上我们自定义的错误码code和错误信息msg，这样就能方便的返回响应体啦！

首先我们自定义一个注解：

```java
/**
 * @author RC
 * @description 自定义参数校验错误码和错误信息注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD}) // 表明该注解只能放在类的字段上
public @interface ExceptionCode {
    // 响应码code
    int value() default 100000;
    // 响应信息msg
    String message() default  "参数校验错误";
}
```

然后我们给参数的字段上加上我们的自定义注解：

```java
@Data
public class User {
    @NotNull(message = "用户id不能为空")
    private Long id;

    @NotNull(message = "用户账号不能为空")
    @Size(min = 6, max = 11, message = "账号长度必须是6-11个字符")
    @ExceptionCode(value = 100001, message = "账号验证错误")
    private String account;

    @NotNull(message = "用户密码不能为空")
    @Size(min = 6, max = 11, message = "密码长度必须是6-16个字符")
    @ExceptionCode(value = 100002, message = "密码验证错误")
    private String password;

    @NotNull(message = "用户邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @ExceptionCode(value = 100003, message = "邮箱验证错误")
    private String email;
}
```

然后我们跑到我们的全局异常处理来进行操作，注意看代码注释：

```java
@RestControllerAdvice
public class ExceptionControllerAdvice {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) throws NoSuchFieldException {
        // 从异常对象中拿到错误信息
        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        // 参数的Class对象，等下好通过字段名称获取Field对象
        Class<?> parameterType = e.getParameter().getParameterType();
        // 拿到错误的字段名称
        String fieldName = e.getBindingResult().getFieldError().getField();
        Field field = parameterType.getDeclaredField(fieldName);
        // 获取Field对象上的自定义注解
        ExceptionCode annotation = field.getAnnotation(ExceptionCode.class);

        // 有注解的话就返回注解的响应信息
        if (annotation != null) {
            return new ResultVO<>(annotation.value(),annotation.message(),defaultMessage);
        }

        // 没有注解就提取错误提示信息进行返回统一错误码
        return new ResultVO<>(ResultCode.VALIDATE_FAILED, defaultMessage);
    }

}
```

这里做了全局异常处理，那么Controller层那边就只用专心做业务逻辑就好了：

```java
@ApiOperation("添加用户")
@PostMapping("/addUser")
public String addUser(@RequestBody @Valid User user) {
    return userService.addUser(user);
}
```

我们来看下效果：



![](https://rudecrab-image-hosting.oss-cn-shenzhen.aliyuncs.com/blog/20200428234819.png)

可以看到，只要加了我们自定义的注解，参数校验失败了就会返回注解的错误码code和错误信息msg。这种做法相比前两种做法带来了以下好处：

* 方便。从之前一大堆手动判断代码，到现在一个注解搞定
* 复用性强。不单单可以对一个对象有效果，对其他受校验的对象都有效果，不用再写多余的代码
* 能够和统一响应码配合。前两种方式是要么就对一个对象所有参数用自定义的错误码，要么就所有参数用统一响应码。这种方式如果你不想为某个字段设置自定义响应码，那么不加注解自然而然就会返回统一响应码

简直不要太方便！**这种方式就像在数据统一响应上加了一个扩展功能，既规范又灵活！**

当然，我这里只是提供了一个思路，我们还可以用自定义注解做很多事情。比如，我们可以让注解直接加在整个类上，让某个类都参数用一个错误码等等！

## 绕过数据统一响应

上面演示了如何让错误码变得灵活，我们继续进一步扩展。

全局统一处理数据响应体会让所有数据都被`ResultVO`包裹起来返还给前端，这样我们前端接到的所有响应都是固定格式的，方便的很。但是！如果我们的接口并不是给我们自己前端所用呢？我们要调用其他第三方接口并给予响应数据，别人要接受的响应可不一定按照code、msg、data来哦！所以，我们还得提供一个扩展性，就是允许绕过数据统一响应！

我想大家猜到了，我们依然要用自定义注解来完成这个功能：

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD}) // 表明该注解只能放在方法上
public @interface NotResponseBody {
}
```

只要加了这个注解的方法，我们就不做数据统一响应处理，返回类型是啥就是返回的啥

```java
@GetMapping("/getUser")
@NotResponseBody
public User getUser() {
    User user = new User();
    user.setId(1L);
    user.setAccount("12345678");
    user.setPassword("12345678");
    user.setEmail("123@qq.com");
    return user;
}
```

我们接下来再数据统一响应处理类里对这个注解进行判断：

```java
@RestControllerAdvice(basePackages = {"com.rudecrab.demo.controller"})
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        // 如果接口返回的类型本身就是ResultVO那就没有必要进行额外的操作，返回false
        // 如果方法上加了我们的自定义注解也没有必要进行额外的操作
        return !(returnType.getParameterType().equals(ResultVO.class) || returnType.hasMethodAnnotation(NotResponseBody.class));
    }
    
    ...
}
```

好，我们来看看效果。没加注解前，数据是被响应体包裹了的：



![](https://rudecrab-image-hosting.oss-cn-shenzhen.aliyuncs.com/blog/20200219205620.png)

方法加了注解后数据就直接返回了数据本身：

![](https://rudecrab-image-hosting.oss-cn-shenzhen.aliyuncs.com/blog/20200429002000.png)



非常好，在数据统一响应上又加了一层扩展。

# 总结

经过一波操作后，我们从没有规范到有规范，再从有规范到扩展规范：

没有规范（一团糟） --> 有规范（缺乏灵活） --> 扩展规范（Nice）

写这篇文章的起因就是我前面所说的，一个网友突然问了我那个问题，**我才赫然发现项目开发中各种各样的情况都可能会出现，没有任何一个架构可以做到完美，与其说我们要去追求完美，倒不如说我们应该要去追求，处理需求变化纷杂的能力！**

最后在这里放上此项目的[github地址](https://github.com/RudeCrab/rude-java/tree/master/project-practice/validation-and-exception-handler2)，clone到本地即可直接运行，并且我将每一次的优化记录都分别做了代码提交，你可以清晰的看到项目的改进过程，如果对你有帮助请在github上点个star，我还会继续更新更多【项目实践】哦！