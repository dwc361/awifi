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
                <Menu.Item key="Seventh">配置管理</Menu.Item>
            </Menu>
        );
    }
}