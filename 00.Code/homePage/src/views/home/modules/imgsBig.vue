<template>
  <div>
    <a-modal
      :visible="visible"
      centered
      :footer="null"
      @cancel='visible=false'
    >
      <img :src="urls" alt="">
    </a-modal>
  </div>
</template>
<script>
export default {
  data() {
    return {
        visible:false,
    };
  },
  props: ["urls"],
  methods:{
      watchPic(){
          this.visible=true;
      }
  }
};
</script>
<style>
img{
    width:100%;
    height:100%;
}
</style>