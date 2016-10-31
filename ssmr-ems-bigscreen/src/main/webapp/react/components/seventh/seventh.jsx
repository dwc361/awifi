import React from 'react';
import ReactDOM from 'react-dom';
import { Row,Col,Button } from 'react-bootstrap';
import ReactMixin from 'react-mixin';
import Reflux from 'reflux'

import store from '../../stores/seventh-store'
import actions from '../../actions/seventh-actions'

export default class Seventh extends React.Component {

    // 切换到一层架构页面
    switch_to_bigscreen_first_display_page() {
        if(!confirm("确定切换到一层架构页面吗？")) {
            return false;
        }
        actions.switch_to_bigscreen_first_display_page();
    }

    // 切换到二层架构页面
    switch_to_bigscreen_second_display_page() {
        if(!confirm("确定切换到二层架构页面吗？")) {
            return false;
        }
        actions.switch_to_bigscreen_second_display_page();
    }

    // 切换到三层架构页面
    switch_to_bigscreen_third_display_page() {
        if(!confirm("确定切换到三层架构页面吗？")) {
            return false;
        }
        actions.switch_to_bigscreen_third_display_page();
    }

    // 切换到四层架构页面
    switch_to_bigscreen_forth_display_page() {
        if(!confirm("确定切换到四层架构页面吗？")) {
            return false;
        }
        actions.switch_to_bigscreen_forth_display_page();
    }

    // 切换到五层架构页面
    switch_to_bigscreen_fifth_display_page() {
        if(!confirm("确定切换到五层架构页面吗？")) {
            return false;
        }
        actions.switch_to_bigscreen_fifth_display_page();
    }

    render() {
        return (
            <div className="content" >
                <div className="layer">
                    <Row className="show-grid title" style={{margin:"0px"}}>监控层</Row>
                    <Row className="show-grid" style={{margin:"0px"}}>
                        <Col md={12}>
                            <Col md={3} onClick={this.switch_to_bigscreen_first_display_page} style={{ background: "#fff"}}><img src={basePathConst+"/awifi/chart_img/tab/first.png"} /><i>一层监控</i></Col>
                            <Col md={3} onClick={this.switch_to_bigscreen_second_display_page} style={{ background: "#fff"}}><img src={basePathConst+"/awifi/chart_img/tab/second.png"} /><i>二层监控</i></Col>
                            <Col md={3} onClick={this.switch_to_bigscreen_third_display_page} style={{ background: "#fff"}}><img src={basePathConst+"/awifi/chart_img/tab/third.png"} /><i>三层监控</i></Col>
                            <Col md={3} onClick={this.switch_to_bigscreen_forth_display_page} style={{ background: "#fff"}}><img src={basePathConst+"/awifi/chart_img/tab/forth.png"} /><i>四层监控</i></Col>
                            <Col md={3} onClick={this.switch_to_bigscreen_fifth_display_page} style={{ background: "#fff"}}><img src={basePathConst+"/awifi/chart_img/tab/fifth.png"} /><i>五层监控</i></Col>
                        </Col>
                    </Row>
                </div>

                <div className="layer" style={{height:"24rem"}}>
                    <Row className="show-grid title" style={{margin:"0px"}}>专题</Row>
                    <Row className="show-grid" style={{margin:"0px"}}>
                        <Col md={12}>
                            <Col md={3} style={{ background: "#fff"}}><img src={basePathConst+"/awifi/chart_img/tab/threme.png"} /><i>专题</i></Col>
                        </Col>
                    </Row>
                </div>

                <div className="layer" style={{height:"4rem",background:"none",border:"none"}}>
                    <span className="btnTab" >保存切换设置</span>
                </div>

            </div>
        );
    }
}

// ES6 mixin写法，通过mixin将store的与组件连接，功能是监听store带来的state变化并刷新到this.state
ReactMixin.onClass(Seventh, Reflux.connect(store));