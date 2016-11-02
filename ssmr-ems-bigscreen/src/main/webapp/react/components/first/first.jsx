import React from 'react';
import ReactDOM from 'react-dom';
import ReactMixin from 'react-mixin';
import Reflux from 'reflux'

import store from '../../stores/second-store'
import actions from '../../actions/second-actions'
import SvgChart from './SvgChart.jsx';

export default class First extends React.Component {
    constructor(props){
        super(props);
        this.state = {wordList:[], id:"", name:"", wordid:"", wordpath:""}
    }

    addWord() {
        var wordList = this.state.wordList;
        var wordpath = this.state.wordpath;
        if(typeof(wordList) != 'undefined'&&typeof(wordpath) != 'undefined') {
            ReactDOM.render(
                <div style={{width:"100%",height:"100%"}}>
                    {
                        wordList.map(function (obj, i) {
                            return <SvgChart key={i} id={obj.id} name={obj.name} wordid={obj.wordid} wordpath={wordpath} style={{display:"inline-block"}} />
                        })
                    }
                </div>,
                document.getElementById('svgCnt'));
        }
    }

    // 预览
    preview() {
        actions.getBigscreenFirstData();
    }

    // 在第一次渲染后调用，只在客户端。之后组件已经生成了对应的DOM结构，可以通过this.getDOMNode()来进行访问。
    // 如果你想和其他JavaScript框架一起使用，可以在这个方法中调用setTimeout, setInterval或者发送AJAX请求等操作(防止异部操作阻塞UI)。
    componentDidMount() {
        actions.getBigscreenFirstData();
    }

    // 在组件接收到新的props或者state但还没有render时被调用。在初始化时不会被调用。
    componentWillUpdate() {}

    // 在组件完成更新后立即调用。在初始化时不会被调用。
    componentDidUpdate() {
        this.addWord();
    }

    render() {
        return (

            <section id="content">
                <section className="wrapper" id="ad">
                    <form id="jq22" className="jq22" name="f1" method="post" >
                        <div id="box1" className="box" name="box">
                            <div  className="item item1" >
                                <header ><h3>广告图片1</h3></header>

                                <hr/>

                                    <div  className="blockLeft col-md-6 col-lg-6 col-sm-6">
                                        <object id="img1"  data="" />
                                          <span className="fa fa-plus-square">
                                            <input id="in1" name="upload" type="file" className="webuploader-element-invisible"  />
                                          </span>

                                    </div>


                                    <div className="blockRight col-md-6 col-lg-6 col-sm-6">
                                        <label className="col-md-4 col-lg-4 col-sm-4">广告名称:</label>
                                        <input id="text1" type="text" name="advertName" value={this.state.name} />
                                            <i className="warnAD">*</i>
                                            <br/>
                                        <label className="col-md-8 col-lg-8 col-sm-8" >URL： </label><br/>
                                        <input id="url111" type="text" name="url" value={this.state.wordid} />
                                    </div>
                                    <p className="note">* 温馨提示:图片格式-jpg; 图片大小（720*360）;</p>
                                    <div className="delete fa fa-times-circle" click-type="delete"></div>
                            </div>



                        </div>

                        <div id="box2" className="box" name="box">
                            <div  className="item item2" >
                                <header ><h3>广告图片2</h3></header>
                                <hr/>
                                    <div  className="blockLeft col-md-6 col-lg-6 col-sm-6">
                                        <object id="img2"  data="" />
                                <span className="fa fa-plus-square">
                                  <input id="in2" name="upload" type="file"  className="webuploader-element-invisible" />
                                </span>


                                            <img id="img" alt="" src="" />
                                    </div>

                                    <div className="blockRight col-md-6 col-lg-6 col-sm-6">
                                        <label className="col-md-4 col-lg-4 col-sm-4">广告名称:</label>
                                        <input id="text2" type="text" name="advertName" value={this.state.name}  />
                                            <i className="warnAD">*</i><br/>
                                            <label className="col-md-8 col-lg-8 col-sm-8" >URL： </label><br/>
                                            <input  type="url" name="url" value={this.state.wordid} />
                                    </div>
                                    <p className="note">* 温馨提示:图片格式-jpg; 图片大小（720*360）;</p>
                                    <div className="delete fa fa-times-circle" click-type="delete"></div>
                            </div>
                        </div>

                        <div id="box3" className="box" name="box">
                            <div  className="item item3">
                                <header ><h3>广告图片3</h3></header>
                                <hr/>
                                    <div  className="blockLeft col-md-6 col-lg-6 col-sm-6">
                                        <object id="img3" alt="" data="" />
                                    <span className="fa fa-plus-square">
                                      <input id="in3" name="upload" type="file" className="webuploader-element-invisible" />
                                    </span>

                                    </div>

                                    <div className="blockRight col-md-6 col-lg-6 col-sm-6" >
                                        <label className="col-md-4 col-lg-4 col-sm-4">广告名称:</label>
                                        <input id="text3" type="text" name="advertName" value={this.state.name} />
                                            <i className="warnAD">*</i><br/>
                                            <label className="col-md-8 col-lg-8 col-sm-8" >URL： </label><br/>
                                            <input type="url" name="url" value={this.state.wordid} />
                                    </div>
                                    <p className="note">* 温馨提示:图片格式-jpg; 图片大小（720*360）;</p>
                                    <div className="delete fa fa-times-circle" click-type="delete"></div>

                            </div>

                        </div>
                    </form>
                </section>

            </section>

        );
    }
}
// ES6 mixin写法，通过mixin将store的与组件连接，功能是监听store带来的state变化并刷新到this.state
ReactMixin.onClass(First, Reflux.connect(store));