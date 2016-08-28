require('./bigscreen.css');

import React from 'react';
import ReactDOM from 'react-dom';

import DynamicChartComponent from './DynamicChartComponent.jsx';
import Line_YHRZ_ChartComponent from './ChartComponent/Line_YHRZ_ChartComponent.jsx';
import Funnel_SBPM_ChartComponent from './ChartComponent/Funnel_SBPM_ChartComponent.jsx';



ReactDOM.render(<DynamicChartComponent />, document.getElementById('funnel_SBPM'))
ReactDOM.render(<Line_YHRZ_ChartComponent />, document.getElementById('Line_YHRZ_Chart'))
ReactDOM.render(<Funnel_SBPM_ChartComponent />, document.getElementById('Funnel_SBPM_Chart'))