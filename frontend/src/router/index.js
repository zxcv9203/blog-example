import {createRouter, createWebHistory} from 'vue-router';
import HomeView from '../views/HomeView.vue';
import WriteView from "@/views/WriteView.vue";
import ReadView from "@/views/ReadView.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            name: 'Home',
            component: HomeView
        },
        {
            path: '/write',
            name: '글쓰기',
            component: WriteView
        },
        {
            path: "/read/:postId",
            name: "read",
            component: ReadView,
            props: true
        }
    ]
});

export default router;
