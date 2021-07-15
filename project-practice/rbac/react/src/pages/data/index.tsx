import React, {useEffect, useState} from 'react';
import {ColumnsType, TablePaginationConfig} from "antd/es/table";
import {Table} from "antd";
import Ajax from "../../api/Ajax";
import DateUtil from "../../utils/DateUtil";

interface Data {
    id: number,
    customerName: string,
    customerPhone: string,
    price: number,
    createTime: Date,
    companyId: number,
    companyName: string
}

// 表设置
const columns: ColumnsType<Data> = [
    {
        title: '序号',
        dataIndex: 'order',
        key: 'order',
        align: 'center',
    },
    {
        title: '客户名',
        dataIndex: 'customerName',
        key: 'customerName',
        align: 'center',
    },
    {
        title: '客户手机',
        key: 'customerPhone',
        dataIndex: 'customerPhone',
        align: 'center',
    },
    {
        title: '订单价格',
        key: 'price',
        dataIndex: 'price',
        align: 'center',
    },
    {
        title: '订单时间',
        key: 'createTime',
        dataIndex: 'createTime',
        align: 'center',
        render: value => {
            return DateUtil.format(new Date(value), "yyyy-MM-dd hh:mm:ss");
        }
    },
    {
        title: '所属公司',
        key: 'companyName',
        dataIndex: 'companyName',
        align: 'center',
    },
];

const Data = props => {
    const [data, setData] = useState<Data[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [pagination, setPagination] = useState<TablePaginationConfig>({
        current: 1,
        total: 100,
        showSizeChanger: false
    });

    /**
     * 表格变化调用的接口
     * @param pagination 分页属性
     */
    const handleTableChange = (pagination) => {
        setPagination({...pagination});
        fetch(pagination);
    };

    // 刚进入页面时访问一次数据
    useEffect(() => {
        fetch(pagination);
        // eslint-disable-next-line
    }, [])

    /**
     * 获取分页数据
     * @param pagination 分页属性
     */
    const fetch = (pagination) => {
        setLoading(true);
        Ajax.get('/data/page/' + pagination.current).then(body => {
            const data = body.data.records;
            data.forEach((item, index) => {
                item.key = index;
                item.order = index + 1;
            });
            // 给数据设置好key，好渲染组件
            setData(data);
            setPagination({...pagination, total: body.data.total});
            setLoading(false);
        });
    }


    return (
        <div>
            <h1>数据管理</h1>
            <Table
                dataSource={data}
                columns={columns}
                loading={loading}
                pagination={pagination}
                onChange={handleTableChange}
            />
        </div>
    );
};

export default Data;
