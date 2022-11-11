export interface Film {
    id: number,
    name: string,
    isNew: boolean,
    description: string,
    image: string,
    time: number,
    [filed:string]:any
}

export interface Seat {
    id: number,
    column: number,
    row: number,
    [filed:string]:any
}

export interface Session {
    id: number,
    film: Film,
    filmId: number,
    cinemaRoom: CinemaRoom,
    cinemaRoomId: number,
    date: number,
    price: number,
    seats: Seat[],
    [filed:string]:any
}

export interface CinemaRoom {
    id: number,
    countRow: number,
    countColumn: number,
    cinema: Cinema,
    cinemaId: number,
    [filed:string]:any
}

export interface Cinema {
    id: number,
    city: string,
    address: string,
    cinemaRoomList: CinemaRoom[],
    [filed:string]:any

}

declare global {
    interface Window {
        restUrl:string
        baseUrl:string
    }
}