import {createRouter, createWebHistory} from 'vue-router';
import HomeView from '../views/HomeView.vue';
import WriteView from "@/views/WriteView.vue";
import ReadView from "@/views/ReadView.vue";
import EditView from "@/views/EditView.vue";

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
        },
        {
            path: "/edit/:postId",
            name: "edit",
            component: EditView,
            props: true
        }
    ]
});

export default router;
