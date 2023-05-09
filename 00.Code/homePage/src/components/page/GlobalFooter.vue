<template>
  <div class="footer">
    <div class="copyright">
      <!-- Copyright -->
      <!-- <a-icon type="copyright"/> -->
      <!-- 2018-2019 <span>江苏安一信息科技有限公司 版权所有</span> -->
    </div>
     <!-- <div class="links"> -->
      <!-- <span>苏ICP备16015225号-2</span> -->
      <!-- <a href="http://www.jeecg.org" target="_blank">JEECG 首页</a>
      <a href="https://github.com/zhangdaiscott/jeecg-boot" target="_blank">
        <a-icon type="github"/>
      </a>
      <a href="https://ant.design/">Ant Design</a>
      <a href="https://vuecomponent.github.io/ant-design-vue/docs/vue/introduce-cn/">Vue Antd</a> -->
    <!-- </div> -->
  </div>
</template>

<script>
  export default {
    name: "LayoutFooter"
  }
</script>

<style lang="scss" scoped>
  .footer {
    padding: 0 16px;
    margin: 48px 0 24px;
    text-align: center;
    span{
       color: rgba(0, 0, 0, .45);
    }
    .links {
      margin-bottom: 8px;

      a {
        color: rgba(0, 0, 0, .45);

        &:hover {
          color: rgba(0, 0, 0, .65);
        }

        &:not(:last-child) {
          margin-right: 40px;
        }
      }
    }
    .copyright {
      color: rgba(0, 0, 0, .45);
      font-size: 14px;
    }
  }
</style>