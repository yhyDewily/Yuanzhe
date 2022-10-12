<template>
  <!-- <div class="EchartPractice">
    <div id="main"></div>
  </div> -->
  <div id="id_mixGrid" style="height: 1000px">
    <div class="table-column">
      <div class="table-row" id="myEchart1"></div>
      <div class="table-row" id="myEchart3"></div>
      <div class="table-row" id="myEchart5"></div>
    </div>
    <div class="table-column">
      <div class="table-row" id="myEchart2"></div>
      <div class="table-row" id="myEchart4"></div>
      <div class="table-row" id="myEchart6"></div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      SM2Sum: 100,
      RSASum: 20,
    };
  },
  methods: {
    drawChart() {
      let myEchart1 = this.$echarts.init(document.getElementById("myEchart1"));
      let myEchart2 = this.$echarts.init(document.getElementById("myEchart2"));
      let myEchart3 = this.$echarts.init(document.getElementById("myEchart3"));
      let myEchart4 = this.$echarts.init(document.getElementById("myEchart4"));
      let myEchart5 = this.$echarts.init(document.getElementById("myEchart5"));
      let myEchart6 = this.$echarts.init(document.getElementById("myEchart6"));

      let option1 = {
        title: {
          text: "在用非对称密钥",
          left: "center",
        },
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "vertical",
          left: "left",
        },
        series: [
          {
            name: "密钥统计",
            type: "pie",
            radius: "50%",
            data: [
              { value: this.SM2Sum, name: "SM2" },
              { value: this.RSASum, name: "RSA" },
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      };
      let option2 = {
        title: {
          text: "在用对称密钥",
          left: "center",
        },
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "vertical",
          left: "left",
        },
        series: [
          {
            name: "密钥统计",
            type: "pie",
            radius: "50%",
            data: [
              { value: 20, name: "DES" },
              { value: 57, name: "SM1" },
              { value: 66, name: "AES" },
              { value: 89, name: "SM4" },

            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      };
      let option3 = {
        title: {
          text: "归档非对称密钥",
          left: "center",
        },
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "vertical",
          left: "left",
        },
        series: [
          {
            name: "密钥统计",
            type: "pie",
            radius: "50%",
            data: [
              { value: 130, name: "SM2" },
              { value: 477, name: "RSA" },
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      };
      let option4 = {
        title: {
          text: "归档对称密钥",
          left: "center",
        },
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "vertical",
          left: "left",
        },
        series: [
          {
            name: "密钥统计",
            type: "pie",
            radius: "50%",
            data: [
              { value: 20, name: "DES" },
              { value: 57, name: "SM1" },
              { value: 66, name: "AES" },
              { value: 89, name: "SM4" },
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      };
      let option5 = {
        title: {
          text: "备用非对称密钥",
        },
        legend: {
          data: ["剩余量"],
        },
        xAxis: {
          type: "category",
          data: ["RSA1024", "RSA2048", "RSA4096", "SM2"],
        },
        yAxis: {
          type: "value",
        },
        series: [
          {
            data: [220, 157, 100, 80],
            type: "bar",
          },
        ],
      };
      let option6 = {
        title: {
          text: "备用对称密钥",
        },
        legend: {
          data: ["剩余量"],
        },
        xAxis: {
          type: "category",
          data: ["SM1", "SM4", "DES", "AES"],
        },
        yAxis: {
          type: "value",
        },
        series: [
          {
            data: [77, 178, 30, 80],
            type: "bar",
          },
        ],
      };
      myEchart1.setOption(option1); 
      myEchart2.setOption(option2);
      myEchart3.setOption(option3);
      myEchart4.setOption(option4);
      myEchart5.setOption(option5);
      myEchart6.setOption(option6);
    },
  },
  mounted() {
    this.drawChart();
  },
};
</script>


<style type="text/css">
.table-column {
  float: left;
  width: 50%;
  border: 0;
  height: 100%;
}
.table-row {
  float: center;
  width: 100%;
  border: 0;
  height: 33%;
  border-right: 1px solid #ccc;
  border-bottom: 1px solid #ccc;
}
</style>

