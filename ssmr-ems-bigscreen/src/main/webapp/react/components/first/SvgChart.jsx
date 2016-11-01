import React from 'react';

class SvgChart extends React.Component {
   render() {
      return (
          <div className="" style={{display:"inline-block",padding:"1rem",position:"relative"}}>
              <div id="" className="part" style={{width: '200px', height: '160px', background: '#aaa'}}>
                  <object data={this.props.wordpath + this.props.name} style={{ width: '100%', height: '100%'}} />
              </div>
              <input type="hidden" id="word_id" name="word_id" value={this.props.id} />
          </div>
      );
   }
}

export default SvgChart;