

import { Row, Col } from 'antd';
import {}
import React from 'react';
import { render } from 'react-dom';
import SiderMenu from './sidermenu.jsx';
import First from '../../html/first.jsx';
import Second from  '../../html/second.jsx';
import Third from  '../../html/third.jsx';
import Forth from  '../../html/forth.jsx';
import Fifth from  '../../html/fifth.jsx';
import Sixth from  '../../html/sixth.jsx';
import Seventh from  '../../html/seventh.jsx';

export default class Index extends React.Component{

    constructor(props){
        super(props);
        this.state = {current:'First'}
    }

    handleClick(e) {
        console.log('click ', e);
        this.setState({
            current: e.key
        });
        //alert(e.key);
    }

    render() {
        var content;
        switch (this.state.current) {
            case 'First':
                content = <First />;
                break;
            case 'Second':
                content = <Second />;
                break;
            case 'Third':
                content = <Third />;
                break;
            case 'Forth':
                content = <Forth />;
                break;
            case 'Fifth':
                content = <Fifth />;
                break;
            case 'Sixth':
                content = <Sixth />;
                break;
            case 'Seventh':
                content = <Seventh />;
                break;

            default:
                content = <First />;
        }



        return (
            <div className="main-div" >
                <Row id="header">
                    <Col span={20}  offset={2}>
                        <div id="line"></div>
                        <div id="head">
                            <div id="logo" className="col-sm-10 col-md-10 col-lg-10">
                                <img className="logo_img" src="http://localhost:8080/ems-chinanet-bigscreen/ems/bigscreen_backstage/main/image/logo.png" />
                            </div>
                            <div id="user" className="col-sm-2 col-md-2 col-lg-2">
                                <div id="innerUr">
                                    <div>
                                        <span className="fa fa-user"></span>
                                        <p id="admin" value="admin">Admin</p>
                                        <button></button>
                                        <NavDropdown id="nav-dropdown" className="dropdown-menu">
                                            <a href="#">退出账号</a>
                                        </NavDropdown>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </Col>
                </Row>
              <Row id="sidebar"  className="nav-collapse" span={4} offset={2}>
                  <Col className="left_part" span={4} offset={2} >
                    <SiderMenu clickEvent={this.handleClick.bind(this)} current={this.state.current} />
                   </Col>
                  <Col id="content" className="right_part" span={16} offset={2} >
                        <div className="context">
                            {content}
                        </div>
                    </Col>
              </Row>
            </div>
    );
    }
}
