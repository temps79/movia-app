import React, {useEffect, useState} from 'react';
import {Film, Session} from "../MovieTypes";
import {Link, useHistory, useParams} from "react-router-dom";
import MovieApi from "../api/MovieApi";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowLeft, faTrashAlt} from "@fortawesome/free-solid-svg-icons";
import CinemaForm from "../components/CinemaForm";
import CinemaRoomList from "./CinemaRoomList";
import SessionForm from "../components/SessionForm";

type SessionFormParams = {
    id: string,
    cinemaRoomId: string,
    sessionId: string,
};

const SessionData = () => {
    let initialSession = {
        id: -1,
        film: {
            id: 1
        },
        cinemaRoom: null,
        date: new Date().getTime(),
        price: 0
    } as Session

    const {id, cinemaRoomId, sessionId} = useParams<SessionFormParams>()
    const [session, setSession] = useState(initialSession)
    const [hasChanged, setHasChanged] = useState<boolean>(false)
    const [isLoading, setIsLoading] = useState<boolean>(true)
    const [films, setFilms] = useState<Film[]>([])
    const navigate = useHistory();


    useEffect(() => {
        let idInt = !!sessionId ? Number(sessionId) : -1
        if (idInt > 0) {
            updateData(idInt)
        } else {
            setSession(initialSession)
            updateFilms()
        }
    }, [])
    const updateData = (id: number) => {
        MovieApi.getSession(id)
            .then(session => {
                    let newSession = session as Session
                    setSession(newSession)
                    updateFilms()
                }
            )
            .catch(err => navigate.push('/'))
    }
    const updateFilms = () => {
        MovieApi.getFilms()
            .then(res => {
                let newFilms = res as Film[]
                setFilms(newFilms)
                setIsLoading(false)
            })
    }
    const onDelete = () => {
        MovieApi.deleteSession(session.id)
            .then(res => {
                navigate.push('/')
            })
    }
    return (
        <section>
            {isLoading ?
                <div role="status">
                    <svg aria-hidden="true"
                         className="mr-2 w-8 h-8 text-gray-200 animate-spin dark:text-gray-600 fill-blue-600"
                         viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
                            fill="currentColor"/>
                        <path
                            d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
                            fill="currentFill"/>
                    </svg>
                    <span className="sr-only">Loading...</span>
                </div>
                :
                <>
                    <header className="bg-white space-y-4 p-4 sm:px-8 sm:py-6 lg:p-4 xl:px-8 xl:py-6   ">
                        <div className="flex items-center justify-between">
                            <h2 className="font-semibold text-slate-900">Session</h2>
                            <div className={'flex'}>
                                {session.id > 0 &&
                                    <button
                                        className={"bg-red-600 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-full "}
                                        onClick={onDelete}>
                                        <FontAwesomeIcon icon={faTrashAlt}/>
                                    </button>
                                }
                                <div style={{
                                    marginTop: '0px ',
                                    marginBottom: '0px ',
                                }}>
                                    <Link to={'/'}>
                                        <button
                                            className={"ml-5 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full "}>
                                            <FontAwesomeIcon icon={faArrowLeft}/>
                                        </button>
                                    </Link>
                                </div>
                            </div>
                        </div>

                    </header>
                    <SessionForm session={session} setSession={setSession} films={films} cinemaId={Number(id)}
                                 cinemaRoomId={Number(cinemaRoomId)}/>
                </>
            }
        </section>
    );
}

export default SessionData;