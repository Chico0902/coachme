<script setup>
import memberAPI from '../apispec/MemberAPI'
import adminAPI from '../apispec/AdminAPI'
import coachAPI from '../apispec/CoachAPI'
import dmAPI from '../apispec/DmAPI'
import coachingAPI from '../apispec/CoachingAPI'
import liveCoachingAPI from '../apispec/LiveCoachingAPI'
import videoCoachingAPI from '../apispec/VideoCoachingAPI'
import likeAPI from '../apispec/LikeAPI'
import reviewAPI from '../apispec/ReviewAPI'
import paymentAPI from '../apispec/PaymentAPI'
import SideIndex from '../components/SideIndex.vue'
const indexes = [
  { id: 'spec-1', title: 'Member API' },
  { id: 'spec-2', title: 'Admin API' },
  { id: 'spec-3', title: 'Coach API' },
  { id: 'spec-4', title: 'DM API' },
  { id: 'spec-5', title: 'Coaching API' },
  { id: 'spec-6', title: 'LiveCoaching API' },
  { id: 'spec-7', title: 'VideoCoaching API' },
  { id: 'spec-8', title: 'Like API' },
  { id: 'spec-9', title: 'Review API' },
  { id: 'spec-10', title: 'Payment API' }
]

const apiSpecs = [
  memberAPI,
  adminAPI,
  coachAPI,
  dmAPI,
  coachingAPI,
  liveCoachingAPI,
  videoCoachingAPI,
  likeAPI,
  reviewAPI,
  paymentAPI
]
</script>
<template>
  <div id="apis">
    <h1 class="mt-3 mb-3">API</h1>
    <SideIndex :indexes="indexes" />
    <hr />

    <h2>index</h2>
    <ol>
      <h4>
        <li class="p-2">
          <a href="#spec-1">Member API</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#spec-2">Admin API</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#spec-3">Coach API</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#spec-4">DM API</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#spec-5">Coaching API</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#spec-6">LiveCoaching API</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#spec-7">VideoCoaching API</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#spec-8">Like API</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#spec-9">Review API</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#spec-10">Payment API</a>
        </li>
      </h4>
    </ol>
    <hr />

    <h2>API Table</h2>
    <template v-for="(apiSpec, index) in apiSpecs" :key="index">
      <h5 :id="`spec-${index + 1}`">{{ apiSpec.title }}</h5>
      <template v-for="api in apiSpec.apis" :key="api.id">
        <li style="list-style-type: circle; font-weight: bold" :id="api.id">{{ api.id }}</li>

        <!-- summery table -->
        <table class="table table-striped">
          <thead>
            <tr>
              <th class="text-center" scope="col">명세</th>
              <th class="text-center" scope="col">METHOD</th>
              <th class="text-center" scope="col">URI</th>
              <th class="text-center" scope="col">권한</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td class="text-center">
                <a :href="`/spec#spec-${api.spec}`">{{ api.spec }}</a>
              </td>
              <td class="text-center">{{ api.method }}</td>
              <td class="text-center">{{ api.uri }}</td>
              <td class="text-center">{{ api.privilege }}</td>
            </tr>
          </tbody>
        </table>

        <!-- description table -->
        <table class="table table-striped">
          <thead>
            <tr>
              <th class="ps-5 text-center" scope="col">설명</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td class="text-center">{{ api.description }}</td>
            </tr>
          </tbody>
        </table>

        <!-- requset & response -->
        <table class="table table-striped">
          <thead>
            <tr>
              <th class="text-center" scope="col" colspan="2">Request</th>
              <th class="text-center" scope="col" colspan="4">Response</th>
            </tr>
          </thead>
          <tbody>
            <!-- dto 이름 -->
            <tr>
              <td v-if="api.request.name != undefined" class="text-center" colspan="2">
                {{ api.request.name }}
              </td>
              <td v-else class="text-center align-middle" colspan="2">없음</td>
              <td class="text-center" colspan="4">
                {{ api.response.name }}
              </td>
            </tr>

            <!-- response body-->
            <tr>
              <td colspan="2">
                <div class="d-flex justify-content-center">
                  <div>
                    <li v-if="api.request.name != undefined">
                      body : {
                      <p class="m-0 ms-5" v-for="(value, key) in api.request.data" :key="key">
                        {{ key }} : {{ value }}
                      </p>
                      <p class="ms-4">}</p>
                    </li>
                    <div v-else>-</div>
                  </div>
                </div>
              </td>
              <td colspan="4" style="vertical-align: baseline">
                <div class="d-flex justify-content-evenly">
                  <div>
                    <tr>
                      <td class="text-center"><strong>성공(success)</strong></td>
                    </tr>
                    <tr>
                      <li>설명 : {{ api.response.success.description }}</li>
                      <li>코드 : {{ api.response.success.code }}</li>
                      <li>
                        body : {
                        <p class="m-0 ms-5" v-for="(value, key) in api.response.success.data" :key="key">
                          {{ key }} : {{ value }}
                        </p>
                        <p class="ms-4">}</p>
                      </li>
                    </tr>
                  </div>
                  <div>
                    <tr>
                      <td class="text-center"><strong>실패(fail)</strong></td>
                    </tr>
                    <tr>
                      <td>
                        <li>설명 : {{ api.response.fail.description }}</li>
                        <li>코드 : {{ api.response.fail.code }}</li>
                        <li>
                          body : {
                          <p class="m-0 ms-5" v-for="(value, key) in api.response.fail.data" :key="key">
                            {{ key }} : {{ value }}
                          </p>
                          <p class="ms-4">}</p>
                        </li>
                      </td>
                    </tr>
                  </div>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
        <br />
      </template>
    </template>
  </div>
</template>
<style scoped>
a {
  text-decoration: none;
}
#apis {
  width: 75%;
}
</style>
