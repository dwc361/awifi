import React from 'react';

class AddChart extends React.Component {
   render() {
      return (
          /*<form action={this.props.path} method="post" enctype="multipart/form-data">
              <input type="file" name="file" /> <input type="submit" value="提交" />
              <input id="chart_path" name="chart_path" value={this.props.path} />
          </form>*/
          <form id="myForm" enctype="multipart/form-data" method="POST">
              <input type="text" name="name" id="name" />
                  <input type="text" name="lastName" id="lastName" />
                      <input type="file" name="files[]" multiple />
                          <button type="submit" class="btn btn-primary start">
                              <i class="glyphicon glyphicon-upload"></i>
                              <span>Start upload</span>
                          </button>
          </form>
      );
   }
}

export default AddChart;