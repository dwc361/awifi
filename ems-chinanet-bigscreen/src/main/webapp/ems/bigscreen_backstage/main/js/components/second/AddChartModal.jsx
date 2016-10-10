import React from 'react';
import {Button, ButtonGroup, ButtonToolbar, Checkbox, Col, ControlLabel, FieldGroup, Form, FormControl, FormGroup, Modal, OverlayTrigger, Popover, Radio, Tooltip} from "react-bootstrap";

//import ChartForm from './ChartForm.jsx';

const AddChartModal = React.createClass({
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
      <div>
        <div style={{display:"inline-block",padding:"1rem"}} onClick={this.open}>
          <div id="add_chart" style={{ width: '142px', height: '110px', background: '#aaa'}}>
              <span className="fa fa-plus fa-spin" style={{display:"inline",lineHeight:"110px",color:"#fff",fontSize:"4rem",textAlign:"center",marginLeft:"54px"}}></span>
          </div>
          <h3 style={{fontSize:"1.4rem",fontWeight:"600",textAlign:"center",lineHeight:"3.2rem"}}>新增</h3>

        </div>
      </div>
    );
  }
});

export default AddChartModal;