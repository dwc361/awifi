import React from 'react';
//var BS = require('react-bootstrap');

class BigscreenPart extends React.Component {
   render() {
      return (
         <div  id={this.props.id} className="part" style={{width:'20%',height:'25%'}}></div>
      );
   }
};

export default BigscreenPart;