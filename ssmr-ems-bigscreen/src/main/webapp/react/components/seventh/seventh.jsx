import React from 'react';
import { Row,Col,Button } from 'react-bootstrap';

export default class Seventh extends React.Component {
    render() {
        return (
            <div className="content" >
               <div className="layer">
                   <Row className="show-grid title">监控层</Row>
                   <Row className="show-grid">
                       <Col md={3}><img src={this.props.url} /></Col>
                       <Col md={3}><img src={this.props.url} /></Col>
                       <Col md={3}><img src={this.props.url} /></Col>
                       <Col md={3}><img src={this.props.url} /></Col>
                       <Col md={3}><img src={this.props.url} /></Col>
                       <Col md={3}><img src={this.props.url} /></Col>
                       <Col md={3}><img src={this.props.url} /></Col>
                       <Col md={3}><img src={this.props.url} /></Col>
                   </Row>
               </div>

                <div className="layer">
                    <Row className="show-grid title">专题</Row>
                    <Row className="show-grid">
                        <Col md={3}><img src={this.props.url} /></Col>
                        <Col md={3}><img src={this.props.url} /></Col>
                        <Col md={3}><img src={this.props.url} /></Col>
                        <Col md={3}><img src={this.props.url} /></Col>
                    </Row>
                </div>

                <Button>保存切换设置</Button>

            </div>
        );
    }
}