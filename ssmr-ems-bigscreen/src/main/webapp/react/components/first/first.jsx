import React from 'react';
import ReactDOM from 'react-dom';
import ReactMixin from 'react-mixin';
import Reflux from 'reflux'

import store from '../../stores/first-store.js'
import actions from '../../actions/first-actions.js'

export default class First extends React.Component {
    constructor(props){
        super(props);
        this.state = {wordList:[], id:"", name:"", wordid:"", wordpath:"", type:""}
    }

    addWord() {
        var wordList = this.state.wordList;
        var wordpath = this.state.wordpath;
        if(typeof(wordList) != 'undefined'&&typeof(wordpath) != 'undefined'&&wordpath != "") {
            wordList.map(function (obj, i) {
                var path = wordpath+obj.name;
                if(obj.type == "1"){
                    $("#welObject").append("<object data='"+path+"' type='image/svg+xml' width='100%' height='100%' />");
                    alert(path);
                    $("#welWordName").val(obj.name);
                    $("#welWordId").val(obj.wordid);
                }else if(obj.type == "2"){
                    $("#celObject").append("<object data='"+path+"' type='image/svg+xml' width='100%' height='100%' />");
                    $("#celWordName").val(obj.name);
                    $("#celWordId").val(obj.wordid);
                }else{
                    $("#annObject").append("<object data='"+path+"' type='image/svg+xml' width='100%' height='100%' />");
                    $("#annWordName").val(obj.name);
                    $("#annWordId").val(obj.wordid);
                }
            })
        }
    }

    // 预览
    preview() {

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
            <div id="content">
                <section id="main-content">
                    <section className="wrapper" id="ad">
                        <form id="firstbackg" className="jq22" name="f1" method="post" >
                            <div className="handle">
                               <span>
                                  <i id="save" className="fa fa-folder-open" onClick={this.saveBigscreenFirstData}></i>
                                  <i id="preview" className="fa fa-eye" onClick={this.previewFirstData}></i>
                               </span>
                            </div>
                            <div id="box1" className="box" name="box">
                                <div  className="item item1" >
                                    <header ><h3>欢迎领导</h3></header>
                                    <hr/>
                                    <div id="welObject" className="blockLeft col-md-6 col-lg-6 col-sm-6">
                                          <span className="fa fa-plus-square">
                                              <input id="in1" name="upload" type="file" className="webuploader-element-invisible"  />
                                          </span>
                                    </div>
                                    <div className="blockRight col-md-6 col-lg-6 col-sm-6">
                                        <label className="col-md-4 col-lg-4 col-sm-4">背景名称：</label>
                                        <input id="welWordName" type="text" name="welWordName" readOnly="true" value="" />
                                        <i className="warnAD">*</i><br/>
                                        <label className="col-md-8 col-lg-8 col-sm-8" >文字：</label><br/>
                                        <input id="welWordId" type="text" name="wordId" value="" />
                                        <div className="checkdiv">
                                            <input type="checkbox" id="welCheckbox" className="checkRight col-md-6 col-lg-6 col-sm-6" checked="checked" />
                                        </div>
                                    </div>
                                    <div className="delete fa fa-times-circle"></div>
                                </div>
                            </div>

                            <div id="box2" className="box" name="box">
                                <div  className="item item2" >
                                    <header ><h3>节日庆祝</h3></header>
                                    <hr/>
                                        <div id="celObject" className="blockLeft col-md-6 col-lg-6 col-sm-6">
                                            <span className="fa fa-plus-square">
                                                <input id="in2" name="upload" type="file"  className="webuploader-element-invisible" />
                                            </span>
                                        </div>
                                        <div className="blockRight col-md-6 col-lg-6 col-sm-6">
                                            <label className="col-md-4 col-lg-4 col-sm-4">背景名称:</label>
                                            <input id="celWordName" type="text" name="celWordName" value=""  />
                                            <i className="warnAD">*</i><br/>
                                            <label className="col-md-8 col-lg-8 col-sm-8" >文字： </label><br/>
                                            <input id="celWordId" type="url" name="celWordId" value="" />
                                            <div className="checkdiv">
                                                <input type="checkbox" id="celCheckbox" className="checkRight col-md-6 col-lg-6 col-sm-6" />
                                            </div>
                                        </div>
                                        <div className="delete fa fa-times-circle"></div>
                                </div>
                            </div>

                            <div id="box3" className="box" name="box">
                                <div  className="item item3">
                                    <header ><h3>通知</h3></header>
                                    <hr/>
                                        <div id="annObject" className="blockLeft col-md-6 col-lg-6 col-sm-6">
                                            <span className="fa fa-plus-square">
                                                <input id="in3" name="upload" type="file" className="webuploader-element-invisible" />
                                            </span>
                                        </div>
                                        <div className="blockRight col-md-6 col-lg-6 col-sm-6" >
                                            <label className="col-md-4 col-lg-4 col-sm-4">背景名称:</label>
                                            <input id="annWordName" type="text" name="annWordName" value="" />
                                            <i className="warnAD">*</i><br/>
                                            <label className="col-md-8 col-lg-8 col-sm-8" >文字：</label><br/>
                                            <input id="annWordId" type="url" name="annWordId" value="" />
                                            <div className="checkdiv">
                                                <input type="checkbox" id="annCheckbox" className="checkRight col-md-6 col-lg-6 col-sm-6" />
                                            </div>
                                        </div>
                                        <div className="delete fa fa-times-circle" ></div>
                                </div>
                            </div>
                        </form>
                    </section>
                </section>
            </div>
        );
    }
}
// ES6 mixin写法，通过mixin将store的与组件连接，功能是监听store带来的state变化并刷新到this.state
ReactMixin.onClass(First, Reflux.connect(store));