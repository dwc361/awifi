import React from 'react';
import ReactDOM from 'react-dom';

const FirstComponent extends = React.createclass({

    render: function() {
    return:(
        <section id="main-content">
                  <section className="wrapper" id="ad">
                    <form id="jq22" className="jq22" name="f1" method="post" >
                          <div id="box1" className="box" name=box>
                            <div  className="item item1" >
                                <header ><h3>广告图片1</h3></header>

                                <hr>

                                <div  className="blockLeft col-md-6 col-lg-6 col-sm-6">
                                  <img id="img1" alt="" src="">
                                  <span className="fa fa-plus-square">
                                    <input id="in1" name="upload" type="file" accept=".jpg,.jpeg,.png"  className="webuploader-element-invisible"  >
                                  </span>


                             </div>


                             <div className="blockRight col-md-6 col-lg-6 col-sm-6">
                                   <label className="col-md-4 col-lg-4 col-sm-4">广告名称:</label>
                                   <input id="text1" type="text" name="advertName" value="">
                                   <i className="warnAD">*</i>
                                   <br>
                                   <label className="col-md-8 col-lg-8 col-sm-8" >URL： </label><br>
                                   <input id="url111" type="url" name="url" value="">
                              </div>
                               <p className="note">* 温馨提示:图片格式-jpg; 图片大小（720*360）;</p>
                              <div className="delete fa fa-times-circle" click-type="delete"></div>
                          </div>



                        </div>

                        <div id="box2" className="box" name=box>
                            <div  className="item item2" >
                                <header ><h3>广告图片2</h3></header>
                                <hr>
                                <div  className="blockLeft col-md-6 col-lg-6 col-sm-6">
                                <img id="img2" alt="" src="">
                                <span className="fa fa-plus-square">
                                  <input id="in2" name="upload" type="file" accept=".jpg,.jpeg,.png" className="webuploader-element-invisible" />
                                </span>


                                   <img id="img" alt="" src="">
                                 </div>

                                 <div className="blockRight col-md-6 col-lg-6 col-sm-6">
                                   <label className="col-md-4 col-lg-4 col-sm-4">广告名称:</label>
                                   <input id="text2" type="text" name="advertName" value="">
                                                          <i className="warnAD">*</i><br>
                                   <label className="col-md-8 col-lg-8 col-sm-8" >URL： </label><br>
                                   <input  type="url" name="url" value="">
                                 </div>
                                    <p className="note">* 温馨提示:图片格式-jpg; 图片大小（720*360）;</p>
                                <div className="delete fa fa-times-circle" click-type="delete"></div>
                            </div>
                          </div>

                        <div id="box3" className="box" name=box>
                            <div  className="item item3">
                                <header ><h3>广告图片3</h3></header>
                                <hr>
                                <div  className="blockLeft col-md-6 col-lg-6 col-sm-6">
                                    <img id="img3" alt="" src="">
                                    <span className="fa fa-plus-square">
                                      <input id="in3" name="upload" type="file" accept=".jpg,.jpeg,.png" className="webuploader-element-invisible" />
                                    </span>

                                </div>

                                <div className="blockRight col-md-6 col-lg-6 col-sm-6" >
                                    <label className="col-md-4 col-lg-4 col-sm-4">广告名称:</label>
                                   <input id="text3" type="text" name="advertName" value="">
                                        <i className="warnAD">*</i><br>
                                   <label className="col-md-8 col-lg-8 col-sm-8" >URL： </label><br>
                                   <input type="url" name="url" value="">
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

        });

export default FirstComponent;