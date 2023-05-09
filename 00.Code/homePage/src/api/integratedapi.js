import { axios } from '@/utils/request'



export function getios_visitorphoto(data) {
    return axios({
      url: '/visitor/getphoto',
      method: 'post',
      data: data
    })
  }

  export function getios_visitorphoto1(data) {
    return axios({
      url: '/visitor/getphoto1',
      method: 'post',
      data: data
    })
  }

  export function getios_visitorphoto2(data) {
    return axios({
      url: '/visitor/getphoto2',
      method: 'post',
      data: data
    })
  }

  export function getchannellistapi(data) {
    return axios({
      url: '/device/channelslist',
      method: 'post',
      data: data
    })
  }

  export function getcameraaddressapi(data) {
    return axios({
      url: '/device/getcameraaddress',
      method: 'post',
      data: data
    })
  }

  export function todayvistorcountapi(data) {
    return axios({
      url: '/bigscreen/todayvistorcount',
      method: 'post',
      data: data
    })
  }
  
  export function getsometodayvistorcountapi(data) {
    return axios({
      url: '/bigscreen/getsometodayvistorcount',
      method: 'post',
      data: data
    })
  }
  
  export function getflowadddressapi(data) {
    return axios({
      url: '/bigscreen/getbigscreenaddress',
      method: 'post',
      data: data
    })
  }
  
  export function updatebigscreenaddressapi(data) {
    return axios({
      url: '/bigscreen/updatebigscreenaddress',
      method: 'post',
      data: data
    })
  }
  
  export function updatesysuserphoto(data) {
    return axios({
      url: '/shipuser/sysuserphoto',
      method: 'post',
      data: data
    })
  }
  
  export function getfacecountapi(data) {
    return axios({
      url: '/bigscreen/getfacecount',
      method: 'post',
      data: data
    })
  }
  
  export function getmonitorFaceRecordListapi(data) {
    return axios({
      url: '/monitorFaceRecord/list'+data,
      method: 'get'
    })
  }

  export function getareaListapi(data) {
    return axios({
      url: '/integrated/area/list'+data,
      method: 'get'
    })
  }

  export function getAreaPermissionapi(data) {
    return axios({
      url: '/integrated/areaPermission/getAreaPermission'+data,
      method: 'get'
    })
  }
  
  export function saveAreaPermissionapi(data) {
    return axios({
      url: '/integrated/areaPermission/saveAreaPermission'+data,
      method: 'get'
    })
  }
  
  
  export function getalladdresslistapi(data) {
    return axios({
      url: '/bigscreen/getalladdresslist'+data,
      method: 'get'
    })
  }

  export function addalladdresslistapi(data) {
    return axios({
      url: '/bigscreen/addalladdresslist'+data,
      method: 'get'
    })
  }
  
  export function getvideoaddressminutesapi(data) {
    return axios({
      url: '/bigscreen/getvideoaddressminutes',
      method: 'get'
    })
  }
  
  export function updatevideoaddressminutesapi(data) {
    return axios({
      url: '/bigscreen/updatevideoaddressminutes'+data,
      method: 'get'
    })
  }

  export function getAlarmweeklistapi(data) {
    return axios({
      url: '/bigscreen/getAlarmweeklist',
      method: 'get'
    })
  }

  export function getAlarStaticapi(data) {
    return axios({
      url: '/bigscreen/getAlarStatic',
      method: 'get'
    })
  }

  export function getpeplistapi(data) {
    return axios({
      url: '/integrated/faceStock/peplist'+data,
      method: 'get'
    })
  }

  export function getcompanylistapi(data) {
    return axios({
      url: '/monitorAlarm/getCompanyList?companyId='+data,
      method: 'get'
    })
  }
  
  

  