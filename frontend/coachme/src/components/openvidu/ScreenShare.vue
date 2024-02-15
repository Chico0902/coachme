<template>
    <div>
        <button @click="publishScreenShare" class="btn"
            :class="{ 'btn-success': screensharing, 'btn-danger': !screensharing }">
            {{ screensharing ? '화면 공유 중' : '화면 공유' }}
        </button>
    </div>
</template>
    
<script>
export default {
    props: {
        OVScreen: {
            type: Object
        },
        sessionScreen: {
            type: Object
        },
        screensharing: {
            type: Boolean
        }
    },
    data() {
        return {
        };
    },
    methods: {
        updateState(value) {
            this.$emit('updateScreensharing', value);
        },
        updatePublisher(value) {
            this.$emit("updatePublisher", value);
        },
        // 화면 공유 메서드
        publishScreenShare() {
            // --- 9.1) To create a publisherScreen set the property 'videoSource' to 'screen'
            var publisherScreen = this.OVScreen.initPublisher(undefined, { videoSource: "screen" });

            // --- 9.2) Publish the screen share stream only after the user grants permission to the browser
            publisherScreen.once('accessAllowed', () => {
                this.updateState(true);

                // If the user closes the shared window or stops sharing it, unpublish the stream
                publisherScreen.stream.getMediaStream().getVideoTracks()[0].addEventListener('ended', () => {
                    this.sessionScreen.unpublish(publisherScreen);
                    this.updateState(false);
                });
                this.sessionScreen.publish(publisherScreen);

                this.updatePublisher(publisherScreen);
            });

            publisherScreen.on('videoElementCreated', function (event) {
                // appendUserData(event.element, sessionScreen.connection);
                // event.element['muted'] = true;
            });

            publisherScreen.once('accessDenied', () => {
                console.error('Screen Share: Access Denied');
            });
        }
    },
};
</script>
  
<style>
.btn {
    font-size: 1.5rem;
    margin-right: 10px;
}
</style>
  