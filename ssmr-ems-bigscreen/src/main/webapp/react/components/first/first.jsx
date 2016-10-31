import React from 'react';
import ReactDOM from 'react-dom';
import ReactMixin from 'react-mixin';
import Reflux from 'reflux'

import store from '../../stores/second-store'
import actions from '../../actions/second-actions'

export default class First extends React.Component {

    render() {
        return (
            <div id="content">
                <div className="handle">
                   <span>
                      <i id="save" className="fa fa-folder-open" onClick={this.saveBigscreenSecondData}></i>
                      <i id="preview" className="fa fa-eye" onClick={this.preview}>预览</i>
                   </span>
                </div>
                <div className="htmleaf-container">
                    <form encType="multipart/form-data">
                        <span className="file-input">
                            <div className="file-preview">
                                <object data="" className="file-preview-image" ></object>
                            </div>
                            <div className="clearfix">

                            </div>
                            <div className="input-group">
                                <div tabIndex="-1" className="form-control file-caption  kv-fileinput-caption kv-has-ellipsis">
                                    <span className="file-caption-ellipsis">...</span>
                                    <div className="file-caption-name">
                                        <span className="glyphicon glyphicon-file kv-caption-icon"></span>
                                    </div>
                                </div>
                                <div className="input-group-btn">
                                    <button type="button" title="清除选中文件" className="btn btn-default fileinput-remove fileinput-remove-button">
                                        <i className="glyphicon glyphicon-trash"></i>移除</button>
                                    <button type="submit" title="上传选中文件" className="btn btn-default kv-fileinput-upload fileinput-upload-button">
                                        <i className="glyphicon glyphicon-upload"></i>上传</button>
                                    <div className="btn btn-primary btn-file">
                                        <i className="glyphicon glyphicon-folder-open"></i><input id="file-0a" className="file" type="file" aria-multiline="true" data-min-file-count="1" />
                                    </div>
                                </div>
                            </div>
                        </span>
                    </form>
                </div>
                <div id="svgCnt"></div>
            </div>
        );
    }
}
