import React from 'react';
import {Breadcrumb} from "antd";
import {useLocation} from 'react-router-dom';

/**
 * 面包屑
 */
const BreadCrumb = props => {
    // 拆解路由路径
    const location = useLocation();
    const pathSnippets = location.pathname.split('/').filter(i => i);
    return (
        <Breadcrumb>
            {
                pathSnippets.map(i =>
                    <Breadcrumb.Item>
                        {i}
                    </Breadcrumb.Item>
                )
            }
        </Breadcrumb>
    );
};

export default BreadCrumb;
