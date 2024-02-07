<!-- 코미와 진실의 방으로 가는 버튼 -->

<script>
import OvVideo from '@/components/molecules/LiveOvVideo.vue';

export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	props: {
		streamManager: Object,
    name : { // 코미 이름
    type : String
  },
	},

	computed: {
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
	},

	methods: {
		getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
	},
};

</script>

<template>
  <div style="width: 14vw; height: 16.5vh; display: flex;">
    <div>
    <ov-video :stream-manager="streamManager" style="width: 14vw; height: 16.5vh;"/>
	  <div><p>{{ clientData }}</p></div>
  </div>
    <q-btn>
      <span class="material-symbols-outlined" style="font-size: 32px;">
        meeting_room
      </span>
      <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <Strong>{{ props.name }} 님과 진실의 방으로 가기</Strong>
      </q-tooltip>
    </q-btn>
  </div>
</template>

<style scoped></style>