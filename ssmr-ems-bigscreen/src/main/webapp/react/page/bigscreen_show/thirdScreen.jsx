import React from 'react';
import ReactDOM from 'react-dom';
import Curve_ZDZX_ChartComponent from '../../components/echarts/ChartComponent/third_charts/Curve_ZDZX/Curve_ZDZX.jsx';
import Pie_YDZD_ChartComponent from '../../components/echarts/ChartComponent/third_charts/Pie_YDZD_ChartComponent/Pie_YDZD_ChartComponent.jsx';
import Mix_SBZT_ChartComponent from '../../components/echarts/ChartComponent/third_charts/Mix_SBZT/Mix_SBZT.jsx';
import Pie_AC_ChartComponent from '../../components/echarts/ChartComponent/third_charts/Pie_AC/Pie_AC.jsx';
import Bar_YYS_ChartComponent from '../../components/echarts/ChartComponent/third_charts/Bar_YYS/Bar_YYS.jsx';
import Line_CPU_ChartComponent from '../../components/echarts/ChartComponent/third_charts/Line_CPU/Line_CPU.jsx';

ReactDOM.render(
    <div>
        <section className="block_part leftClum">
            <Pie_YDZD_ChartComponent/>
        </section>
        <section className="block_part rightClum">
            <Curve_ZDZX_ChartComponent/>
        </section>
        <section className="block_part rightClum third_part">
            <Mix_SBZT_ChartComponent/>
        </section>
        <section className="block_part leftClum">
            <Pie_AC_ChartComponent/>
        </section>
        <section className="block_part rightClum">
            <Bar_YYS_ChartComponent/>
        </section>
        <section className="block_part rightClum">
            <Line_CPU_ChartComponent/>
        </section>
    </div>,
    document.getElementById('thirdScreen_show'));