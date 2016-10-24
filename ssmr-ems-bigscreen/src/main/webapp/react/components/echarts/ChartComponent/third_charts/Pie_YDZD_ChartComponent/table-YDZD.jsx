import React from 'react';
import { Table } from 'antd';

export default class Table_YDZD extends React.Component{
    constructor(props) {
        super(props);
        this.showCurRowMessquantity = this.showCurRowMessquantity.bind(this);
    }
    componentDidMount() {

    }
    showCurRowMessquantity(record){
        alert(11);
        alert("key:"+record.key + " name:"+record.name + " quantity:" + record.quantity + " percent:" + record.percent);
    }
    //展示当前行信息

    render(){

        let self = this;
        console.log(self);
        const columns = [
            { title: "厂商", dataIndex: "name", key: "name" },
            { title: "数量", dataIndex: "quantity", key: "quantity"},
            { title: "百分比()%", dataIndex: "percent", key: "percent" }
            ];
        const data = [
            { key: 1, name: "apple", quantity: 951, percent: 39.92},
            { key: 2, name: "未知", quantity: 543, percent: 22.80},
            { key: 3, name: "Huaewi", quantity: 132, percent: 5.54},
            { key: 4, name: "Sangxing", quantity: 124, percent: 5.21},
            { key: 5, name: "Xiaomi", quantity: 111, percent: 4.66}
        ];

        const rowSelection = {
            onChange(selectedRowKeys, selectedRows) {
                console.log(`selectedRowKeys: ${selectedRowKeys}`, "selectedRows: ", selectedRows);
            },
            onSelect(record, selected, selectedRows) {
                console.log(record, selected, selectedRows);
            },
            onSelectAll(selected, selectedRows, changeRows) {
                console.log(selected, selectedRows, changeRows);
            }
        };
        return(
            <Table columns={columns}
                   rowSelection={rowSelection}
                   dataSource={data}
                   className="table"
            />
        );
    }
};
