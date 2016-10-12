import React from 'react';

class SourceFillBigscreen extends React.Component {
    render() {
        return (
            <div style={{ width: '100%', height: '100%',display:"inline-block",padding:"0",position:"relative"}}>
                <div id="modularImg" className="part" style={{width: '100%', height: '84%',position:"absolute",top:"16%"}}>
                    <img src={this.props.url} style={{ width: '100%', height: '100%'}}/>
                </div>
                <div className="heading" style={{width:"100%",height:"16%",background:"#444",color:"#fff",position:"absolute",top:"0"}}>
                    <h3 style={{fontSize:"1.4rem",fontWeight:"600",textAlign:"center",lineHeight:"3.2rem"}}>{this.props.title}</h3>
                </div>
                <input type="hidden" id="chart_id" name="chart_id" value={this.props.id} />
                <input type="hidden" id="chart_path" name="chart_path" value={this.props.path} />
                <input type="hidden" id="target_name" name="target_name" value={this.props.target} />
                <i className="part-clean fa fa-close" />
            </div>
        );
    }
};

export default SourceFillBigscreen;