import React from 'react';

class AddChart extends React.Component {
   render() {
      return (
          <div>
              <div id="chart_add" style={{display:"inline-block",padding:"1rem"}}>
                  <div style={{ width: '142px', height: '110px', background: '#aaa'}}>
                      <span className="fa fa-plus" style={{display:"inline",lineHeight:"110px",color:"#fff",fontSize:"4rem",textAlign:"center",marginLeft:"54px"}}></span>
                  </div>
                  <h3 style={{fontSize:"1.4rem",fontWeight:"600",textAlign:"center",lineHeight:"3.2rem"}}>新增</h3>
              </div>
          </div>
      );
   }
}

export default AddChart;