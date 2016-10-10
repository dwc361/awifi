////import { Menu, Icon } from 'antd';
//import React from 'react'
////const SubMenu = Menu.SubMenu;
//
//export default class SiderMenu extends React.Component {
//    constructor(props) {
//        super(props);
//    }
//
//    render() {
//        return (
//            <ul onClick={this.props.clickEvent}
//                style={{ width: '100%' }}
//                theme={'dark'}
//                defaultOpenKeys={['sub1']}
//                selectedKeys={[this.props.current]}
//                mode="inline"
//            >
//                <li key="sub1" ><span>一层监控</span></li>
//                <li key="sub2" ><span>二层监控</span></li>
//                <li key="sub3" ><span>三层监控</span></li>
//                <li key="sub4" ><span>四层监控</span></li>
//                <li key="sub5" ><span>五层监控</span></li>
//                <li key="sub6" ><span>专题</span></li>
//                <li key="sub7" ><span>配置管理</span></li>
//
//            </ul>
//    );
//    }
//}


import { Menu, Icon } from 'antd';
import React from 'react';
import { render } from 'react-dom';
const SubMenu = Menu.SubMenu;

export default class SiderMenu extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Menu onClick={this.props.clickEvent}
                  style={{ width: '100%' }}
                  theme={'dark'}

                  selectedKeys={[this.props.current]}
                  mode="inline">

                    <Menu.Item key="First">一层监控</Menu.Item>
                    <Menu.Item key="Second">二层监控</Menu.Item>
                    <Menu.Item key="Third">三层监控</Menu.Item>
                    <Menu.Item key="Forth">四层监控</Menu.Item>
                    <Menu.Item key="Fifth">五层监控</Menu.Item>
                    <Menu.Item key="Sixth">专题</Menu.Item>
                    <Menu.Item key="Seventh">切换</Menu.Item>

            </Menu>
        );
    }
}
