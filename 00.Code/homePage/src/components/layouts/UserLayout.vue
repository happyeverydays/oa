<template>
  <div id="userLayout" :class="['user-layout-wrapper', device]">
    <div class="container">
      <div class="top">
        <!-- <div class="header">
          <a href="/"> -->
            <!-- <img src="~@/assets/log.png" class="logo" alt="logo"> -->
            
          <!-- </a>
        </div> -->
        <div class="desc">
         <!-- <span class="title">欢迎登陆</span> -->
        </div>
      </div>
       <!-- <img src="@/assets/logo-new.png" class="logo-png" alt=""> -->
       <img src="@/assets/login.png" class="login-png" alt="">
      <route-view></route-view>

      <!-- <div class="footer">
      
        <div class="copyright">
          Copyright &copy; 2018-2019 <a href="javascript:;" target="_blank">江苏安一信息科技有限公司</a> 版权所有
        </div>
        <div class="links">
          <span>苏ICP备16015225号-2</span>
        </div>
      </div> -->
    </div>
  </div>
</template>

<script>
  import RouteView from "@/components/layouts/RouteView"
  import { mixinDevice } from '@/utils/mixin.js'

  export default {
    name: "UserLayout",
    components: { RouteView },
    mixins: [mixinDevice],
    data () {
      return {}
    },
    mounted () {
      document.body.classList.add('userLayout')
    },
    beforeDestroy () {
      document.body.classList.remove('userLayout')
    },
  }
</script>

<style lang="scss" scoped>
.logo-png{
  position: fixed;
  width:250px;
  top:60px; 
  left:80px;
}
   .login-png{
      width:550px;
      position:fixed;
      left:15%;
    }
  #userLayout.user-layout-wrapper {
    height: 100%;

    &.mobile {
      .container {
        .main {
          max-width: 368px;
          width: 98%;
        }
      }
    }

    .container {
      width: 100%;
      min-height: 100%;
      background: url(~@/assets/bg.png) no-repeat 50%;
      background-size: 100%;
      padding: 110px 0 144px;
      // box-sizing: border-box;
      position: relative;

      a {
        text-decoration: none;
      }

      .top {
        text-align: center;

        .header {
          height: 44px;
          line-height: 44px;

          .badge {
            position: absolute;
            display: inline-block;
            line-height: 1;
            vertical-align: middle;
            margin-left: -12px;
            margin-top: -10px;
            opacity: 0.8;
          }

          .logo {
            height: 44px;
            vertical-align: top;
            margin-right: 16px;
            border-style: none;
          }

          .title {
            font-size: 33px;
            color: rgba(0, 0, 0, .85);
            font-family: "Chinese Quote", -apple-system, BlinkMacSystemFont, "Segoe UI", "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "Helvetica Neue", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
            font-weight: 600;
            position: relative;
            top: 2px;
          }
        }
        .desc {
          font-size: 14px;
          color: rgba(0, 0, 0, 0.45);
          margin-top: 12px;
          margin-bottom: 40px;
          .title {
            font-size: 33px;
            color: rgba(0, 0, 0, .85);
            font-family: "Chinese Quote", -apple-system, BlinkMacSystemFont, "Segoe UI", "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "Helvetica Neue", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
            font-weight: 600;
            position: relative;
            top: 2px;
          }
        }
      }

      .main {
        min-width: 260px;
        width: 368px;
        // margin: 0 auto;
        margin-left:55%;
     
      }

      .footer {
        position: absolute;
        width: 100%;
        bottom: 0;
        padding: 0 16px;
        margin: 48px 0 24px;
        text-align: center;

        .links {
          margin-bottom: 8px;
          font-size: 14px;
          span{
             color: rgba(0, 0, 0, 0.45);
          }
          a {
            color: rgba(0, 0, 0, 0.45);
            transition: all 0.3s;
            &:not(:last-child) {
              margin-right: 40px;
            }
          }
        }
        .copyright {
          color: rgba(0, 0, 0, 0.45);
          font-size: 14px;
        }
      }
    }
  }
</style>