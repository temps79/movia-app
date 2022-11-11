import axios, {AxiosResponse} from "axios";
import {Cinema, CinemaRoom, Film, Seat, Session} from "../MovieTypes";


export default {
    getCinemaRoom() {
        return axios.get(window.restUrl + "/v1/cinema_room/")
            .then(res => res.data)
            .catch(err => console.error(err));
    },
    getCinemaRoomById(id: number): Promise<CinemaRoom | void> {
        return axios.get<CinemaRoom, AxiosResponse<CinemaRoom>>(window.restUrl + "/v1/cinema_room/" + id)
            .then(res => res.data)
            .catch(err => console.error(err));
    },
    getCinemas(): Promise<Cinema[] | void> {
        return axios.get<Cinema[], AxiosResponse<Cinema[]>>(window.restUrl + "/v1/cinema/")
            .then((res) => res.data)
            .catch(err => console.error(err));
    },
    getCinema(id: number): Promise<Cinema | void> {
        return axios.get<Cinema, AxiosResponse<Cinema>>(window.restUrl + `/v1/cinema/${id}`)
            .then((res) => res.data)
            .catch(err => console.error(err));
    },
    saveCinema(id: number, cinema: Cinema): Promise<Cinema | void> {
        return axios.post<Cinema, AxiosResponse<Cinema>>(window.restUrl + `/v1/cinema/${id}`, cinema)
            .then((res) => res.data)
            .catch(err => console.error(err));
    },
    deleteCinemaRoom(id: number): Promise<void> {
        return axios.delete(window.restUrl + `/v1/cinema_room/${id}`)
            .then((res) => res.data)
            .catch(err => console.error(err));
    },
    saveCinemaRoom(id: number, cinemaRoom: CinemaRoom): Promise<Cinema | void> {
        return axios.post(window.restUrl + `/v1/cinema_room/${id}`, cinemaRoom)
            .then((res) => res.data)
            .catch(err => console.error(err));
    },
    getFilm(id: number): Promise<Film | void> {
        return axios.get<Film, AxiosResponse<Film>>(window.restUrl + `/v1/film/${id}`)
            .then((res) => res.data)
            .catch(err => console.error(err));
    },
    getFilms(): Promise<Film[] | void> {
        return axios.get<Film[], AxiosResponse<Film[]>>(window.restUrl + `/v1/film/`)
            .then((res) => res.data)
            .catch(err => console.error(err));
    },
    saveFilm(id: number, film: Film): Promise<Film | void> {
        return axios.post<Film, AxiosResponse<Film>>(window.restUrl + `/v1/film/${id}`, film)
            .then((res) => res.data)
            .catch(err => console.error(err));
    },
    deleteCinema(id: number): Promise<void> {
        return axios.delete(window.restUrl + `/v1/cinema/${id}`)
            .then((res) => res.data)
            .catch(err => console.error(err));
    },
    deleteSession(id: number): Promise<void> {
        return axios.delete(window.restUrl + `/v1/session/${id}`)
            .then((res) => res.data)
            .catch(err => console.error(err));
    },
    deleteFilm(id: number): Promise<void> {
        return axios.delete(window.restUrl + `/v1/film/${id}`)
            .then((res) => res.data)
            .catch(err => console.error(err));
    },
    getSessions(cinemaRoomId: number): Promise<Session[] | void> {
        return axios.get<Session[], AxiosResponse<Session[] | void>>(window.restUrl + `/v1/session/cinema_room_id/${cinemaRoomId}`)
            .then((res) => res.data)
            .catch(err => console.error(err));
    },
    getSession(sessionId: number): Promise<Session | void> {
        return axios.get<Session, AxiosResponse<Session | void>>(window.restUrl + `/v1/session/${sessionId}`)
            .then((res) => res.data)
            .catch(err => console.error(err));
    },
    saveSession(id: number, session: Session): Promise<Session | void> {
        return axios.post<Session, AxiosResponse<Session>>(window.restUrl + `/v1/session/${id}`, session)
            .then((res) => res.data)
            .catch(err => console.error(err));
    },
    getCurrentAccessLevelUser(): Promise<Number | void> {
        return axios.get<Number, AxiosResponse<Number>>(window.restUrl + `/v1/user/`)
            .then((res) => res.data)
            .catch(err => console.error(err));
    },
    logout(): Promise<void> {
        return axios.get(window.baseUrl + `/logout`)
            .then((res) => res.data)
            .catch(err => console.error(err));
    },
    updateSeatSession(id:number,seat:Seat): Promise<void> {
        return axios.post(window.restUrl + `/v1/session/seat/${id}`, seat)
            .then((res) => res.data)
            .catch(err => alert('The place is already booked'));
    },
}