import React, {useState} from 'react';
import {Session} from "../MovieTypes";
import {Link} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faEdit, faSave} from "@fortawesome/free-solid-svg-icons";
import moment from 'moment';
import {useCookies} from "react-cookie";
import ReservationDialog from "./ReservationDialog";

interface SessionListComponentProps {
    sessions: Session[],
}

const SessionListComponent = ({sessions}: SessionListComponentProps) => {
    return (
        <ul className="divide-y divide-slate-100">
            {sessions.map((session) => (
                <ListItem key={session.id} session={session}/>
            ))}
        </ul>
    );
};

export default SessionListComponent;

interface ListItemProps {
    session: Session,
}

const ListItem = ({session}: ListItemProps) => {
    const [cookies, setCookie, removeCookie] = useCookies(['lvl']);
    const [isAdmin, setIsAdmin] = useState<boolean>(!!cookies.lvl && Number(cookies.lvl) === 100)
    const [isUser, setIsUser] = useState<boolean>(!!cookies.lvl && Number(cookies.lvl) === 10)
    const [showModal, setShowModal] = useState<boolean>(false)
    const handleOpen = () => setShowModal(!showModal);

    const getFormattedDate = (date: number) => {
        const currentDateTimeFormat = 'YYYY.MM.DD HH:mm:ss';
        return moment(date).format(currentDateTimeFormat);
    }
    return (
        <article className="flex items-start space-x-6 p-6">
            <img src={session.film.image} alt="" width="60" height="88" className="flex-none rounded-md bg-slate-100"/>
            <div className="min-w-0 relative flex-auto">
                <h2 className="font-semibold text-slate-900 truncate pr-20">{session.film.name}</h2>
                <dl className="mt-2 flex flex-wrap text-sm leading-6 font-medium">
                    {session.film.isNew &&
                        <div className="absolute top-0 right-0 flex items-center space-x-1">
                            {isAdmin &&
                                <Link
                                    to={'/cinema/' + session.cinemaRoom.cinemaId + '/session/' + session.cinemaRoom.id + '/' + session.id}>
                                    <button
                                        className={"ml-5 bg-orange-500 hover:bg-orange-700 text-white font-bold py-2 px-4 rounded-full "}>
                                        <FontAwesomeIcon icon={faEdit}/>
                                    </button>
                                </Link>
                            }
                            {isUser &&
                                <button
                                    className="block text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                                    type="button" data-modal-toggle="defaultModal" onClick={() => setShowModal(true)}>
                                    Reservation
                                </button>
                            }
                            <dt className="text-sky-500">
                                <span className="sr-only">Star rating</span>
                                <svg width="16" height="20" fill="currentColor">
                                    <path
                                        d="M7.05 3.691c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.372 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.539 1.118l-2.8-2.034a1 1 0 00-1.176 0l-2.8 2.034c-.783.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.363-1.118L.98 9.483c-.784-.57-.381-1.81.587-1.81H5.03a1 1 0 00.95-.69L7.05 3.69z"/>
                                </svg>
                            </dt>

                        </div>
                    }


                    <div>
                        <dt className="sr-only">Runtime</dt>
                        <dd className="flex items-center">
                            <svg width="2" height="2" fill="currentColor" className="mx-2 text-slate-300"
                                 aria-hidden="true">
                                <circle cx="1" cy="1" r="1"/>
                            </svg>
                            {Math.round(session.film.time / 60)}hr {session.film.time - Math.round(session.film.time / 60) * 60}mn
                        </dd>
                    </div>
                    <div>
                        <dt className="sr-only">datetime</dt>
                        <dd className="flex items-center">
                            <svg width="2" height="2" fill="currentColor" className="mx-2 text-slate-300"
                                 aria-hidden="true">
                                <circle cx="1" cy="1" r="1"/>
                            </svg>
                            {getFormattedDate(session.date)}
                        </dd>
                    </div>
                    <div>
                        <dt className="sr-only">price</dt>
                        <dd className="flex items-center">
                            <svg width="2" height="2" fill="currentColor" className="mx-2 text-slate-300"
                                 aria-hidden="true">
                                <circle cx="1" cy="1" r="1"/>
                            </svg>
                            {session.price}$
                        </dd>
                    </div>
                </dl>
            </div>
            {isUser &&
                <ReservationDialog showModal={showModal} handleOpen={handleOpen} session={session}/>
            }

        </article>
    )
}