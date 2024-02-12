<template>
	<div>
		<!-- <div id="session-header">
			<h1 id="session-title"></h1>
			<input class="btn btn-sm btn-danger" type="button" id="buttonCloseSession" onmouseup="closeSession()"
				value="Close session">
			<input class="btn btn-sm btn-danger" type="button" id="buttonLeaveSession"
				onmouseup="removeUser(); leaveSession()">
			<div class="vertical-separator-top"></div>
			<input class="form-control" id="forceValue" type="text" onkeyup="checkBtnsForce()">
			<input class="btn btn-sm" type="button" id="buttonForceUnpublish" onmouseup="forceUnpublish()" disabled>
			<input class="btn btn-sm" type="button" id="buttonForceDisconnect" onmouseup="forceDisconnect()" disabled>
			<div class="vertical-separator-top"></div>
			<input class="btn btn-sm" type="button" id="buttonFetchInfo" onmouseup="fetchInfo()" value="Fetch info">
			<input class="btn btn-sm" type="button" id="buttonFetchAll" onmouseup="fetchAll()" value="Fetch all">
		</div> -->
		<div id="video-container" class="col-md-12"></div>

		<div id="recording-btns">
			<div class="btns">
				<input class="btn btn-md" type="button" id="buttonStartRecording" @click="startRecording"
					value="Start recording">
				<form>
					<label class="radio-inline">
						<input type="radio" name="outputMode" value="COMPOSED" id="radio-composed" checked>COMPOSED
					</label>
					<label class="radio-inline">
						<input type="radio" name="outputMode" value="INDIVIDUAL" id="radio-individual">INDIVIDUAL
					</label>
				</form>
				<form>
					<label class="checkbox-inline">
						<input type="checkbox" id="has-audio-checkbox" checked>Has audio
					</label>
					<label class="checkbox-inline">
						<input type="checkbox" id="has-video-checkbox" checked>Has video
					</label>
				</form>
			</div>
			<div class="btns">
				<input class="btn btn-md" type="button" id="buttonListRecording" @click="listRecordings()"
					value="List recordings">
				<div class="vertical-separator-bottom"></div>
				<input class="btn btn-md" type="button" id="buttonGetRecording" @click="getRecording()"
					value="Get recording">
				<input class="btn btn-md" type="button" id="buttonStopRecording" @click="stopRecording()"
					value="Stop recording">
				<input class="btn btn-md" type="button" id="buttonDeleteRecording" @click="deleteRecording()"
					value="Delete recording" disabled>
				<input class="form-control" id="forceRecordingId" v-model="forceRecordingId" type="text" @click="checkBtnsRecordings()">
			</div>
			<div class="textarea-container" id="textarea-http-container">
				<button type="button" class="btn btn-outline-secondary" id="clear-http-btn"
					onclick="clearHttpTextarea()">Clear</button>
				<span>HTTP responses</span>
				<textarea id="textarea-http" readonly="true" class="form-control" name="textarea-http"></textarea>
			</div>
			<div class="textarea-container" id="textarea-events-container">
				<button type="button" class="btn btn-outline-secondary" id="clear-events-btn"
					onclick="clearEventsTextarea()">Clear</button>
				<span>OpenVidu events</span>
				<textarea id="textarea-events" readonly="true" class="form-control" name="textarea-events"></textarea>
			</div>
		</div>
	</div>
</template>

<script>
import axios from "axios";

export default {
	name: 'RecordingView',
	props: {
		session: {
			type: String
		}
	},

	mounted() {
	},

	data() {
		return{
			forceRecordingId : ""
		}
	},

	methods: {
		async startRecording() {
			var outputMode = "COMPOSED";
			var hasAudio = true;
			var hasVideo = true;
			console.log(this.session);
			const response = await axios.post('http://localhost:7777/api/live/recording/start',
				{
					sessionId: this.session,
					outputMode: outputMode,
					hasAudio: hasAudio,
					hasVideo: hasVideo
				},
				{
					headers: { 'Content-Type': 'application/json', },
				});
			console.log(response.data.id);
			console.log(response);
			console.log("녹화중");
			this.forceRecordingId = response.data.id;


		},
		async stopRecording() {
			console.log(this.forceRecordingId);
			const response = await axios.post('http://localhost:7777/api/live/recording/stop',
				{
					recording: this.forceRecordingId
				},
				{
					headers: { 'Content-Type': 'application/json', },
				});
			console.log(response);
		},
		async getRecording(){
			console.log(this.forceRecordingId);
			const response = await axios.get('http://localhost:7777/api/live/recording/get/SessionA~5',
				{
					headers: { 'Content-Type': 'application/json', },
				});
			console.log(response);
		}
		

	}
};
</script>
