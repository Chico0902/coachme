<template>
  <div v-if="streamManager">
    <ov-video :stream-manager="streamManager" style="width: 20vw;" />
  </div>
</template>

<script>
import OvVideo from '@/components/openvidu/OvVideo.vue'

export default {
  name: 'UserVideo-coame',

  components: {
    OvVideo
  },

  props: {
    streamManager: Object
  },

  computed: {
    clientData() {
      const { clientData } = this.getConnectionData()
      return clientData
    }
  },

  methods: {
    getConnectionData() {
      const { connection } = this.streamManager.stream
      return JSON.parse(connection.data)
    }
  }
}
</script>
