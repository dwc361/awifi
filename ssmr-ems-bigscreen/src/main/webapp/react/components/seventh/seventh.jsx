import React from 'react';
import { Row,Col,Button } from 'react-bootstrap';

export default class Seventh extends React.Component {

    render() {
        var imgList1 = [
            <Col md={3} style={{ background: "#fff"}}><img  src={basePathConst+"/awifi/chart_img/tab/first.png"} /><i>一层监控</i></Col>,
            <Col md={3} style={{ background: "#fff"}}><img  src={basePathConst+"/awifi/chart_img/tab/second.png"} /><i>二层监控</i></Col>,
            <Col md={3} style={{ background: "#fff"}}><img  src={basePathConst+"/awifi/chart_img/tab/third.png"} /><i>三层监控</i></Col>,
            <Col md={3} style={{ background: "#fff"}}><img  src={basePathConst+"/awifi/chart_img/tab/forth.png"} /><i>四层监控</i></Col>,
            <Col md={3} style={{ background: "#fff"}}><img  src={basePathConst+"/awifi/chart_img/tab/fifth.png"} /><i>五层监控</i></Col>

        ];


        var imgList2 = [
            <Col md={3} style={{ background: "#fff"}}><img  src={basePathConst+"/awifi/chart_img/tab/threme.png"} /><i>专题</i></Col>
        ];

        return (
            <div className="content" >
               <div className="layer">
                   <Row className="show-grid title" style={{margin:"0px"}}>监控层</Row>
                   <Row className="show-grid" style={{margin:"0px"}}>
                       <Col md={12}>{imgList1}</Col>
                   </Row>
               </div>

                <div className="layer" style={{height:"24rem"}}>
                    <Row className="show-grid title" style={{margin:"0px"}}>专题</Row>
                    <Row className="show-grid" style={{margin:"0px"}}>
                        <Col md={12}>{imgList2}</Col>
                    </Row>
                </div>

                <div className="layer" style={{height:"4rem",background:"none",border:"none"}}>
                    <span className="btnTab" >保存切换设置</span>
                </div>

            </div>
        );
    }
}


