import React from 'react';
import {Button, ButtonGroup, ButtonToolbar, Checkbox, Col, ControlLabel, FieldGroup, Form, FormControl, FormGroup, Modal, OverlayTrigger, Popover, Radio, Tooltip} from "react-bootstrap";

//import ChartForm from './ChartForm.jsx';

const DragChartModal = React.createClass({
  getInitialState() {
    return { showModal: false };
  },

  close() {
    this.setState({ showModal: false });
  },

  open() {
    this.setState({ showModal: true });
  },

  render() {
    return (
      <div className="draggable"  style={{display:"inline-block",padding:"1rem",position:"relative"}}>
         <div id="modularImg" className="part" style={{width: '142px', height: '110px', background: '#aaa'}}>
            <img src={this.props.url} style={{ width: '100%', height: '100%'}}/>
             <span className="edit">
                 <i className="icon fa fa-pencil-square-o" style={{marginLeft:"0"}} onClick={this.open} ></i>
                 <i className="icon fa fa-trash-o"></i>
             </span>
         </div>
         <div className="heading">
            <h3 style={{fontSize:"1.4rem",fontWeight:"600",textAlign:"center",lineHeight:"3.2rem"}}>{this.props.title}</h3>
         </div>

      </div>
    );
  }
});

export default DragChartModal;