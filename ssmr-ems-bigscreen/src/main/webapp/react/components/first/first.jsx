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
            <div id="content">
                <div className="handle">
                   <span>
                      <i id="save" className="fa fa-folder-open"></i>
                      <i id="preview" className="fa fa-eye" onClick={this.preview}></i>
                   </span>
                </div>
                <div className="htmleaf-container">
                    <form encType="multipart/form-data">
                        <span className="">
                            <div id="svgCnt" className="file-preview">
                            </div>
                            <div className="">
                                <input type="text" id="name" title="文件名称" value={this.state.name}/>
                                <input type="text" id="wordId" title="内容" value={this.state.wordid}/>
                                <div className="input-group-btn">
                                    <button type="button" title="修改" className="btn btn-primary">
                                    <i className="fa fa-pencil-square-o"></i>修改</button>
                                    <button type="button" title="清除选中文件" className="btn btn-primary">
                                        <i className="fa fa-trash-o"></i>删除</button>
                                    <button type="submit" title="上传选中文件" className="btn btn-primary">
                                        <i className="fa fa-level-up"></i>上传</button>
                                    <div className="btn btn-primary btn-file">
                                        <i className="fa fa-plus">选择...</i><input id="file-0a" className="file" type="file" aria-multiline="true" data-min-file-count="1" />
                                    </div>
                                </div>
                            </div>
                        </span>
                    </form>
                </div>
            </div>
        );
    }
}
// ES6 mixin写法，通过mixin将store的与组件连接，功能是监听store带来的state变化并刷新到this.state
ReactMixin.onClass(First, Reflux.connect(store));