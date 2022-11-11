import React, {useEffect, useState} from 'react';
import {Cinema, CinemaRoom} from "../MovieTypes";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCircle, faSave, faTrashAlt} from "@fortawesome/free-solid-svg-icons";
import MovieApi from "../api/MovieApi";
import {Link} from "react-router-dom";
import {useCookies} from "react-cookie";

interface CinemaRoomListProps {
    cinemaRoomList: CinemaRoom[],
    updateData: (id: number) => void
    cinemaId: number
}

const CinemaRoomList = ({cinemaRoomList, updateData, cinemaId}: CinemaRoomListProps) => {
    const [cinemaRoomListState, setCinemaRoomListState] = useState<CinemaRoom[]>(cinemaRoomList)
    const [cookies, setCookie, removeCookie] = useCookies(['lvl']);
    const [isAdmin, setIsAdmin] = useState<boolean>(!!cookies.lvl && Number(cookies.lvl) === 100)

    useEffect(() => {
        let newCinemaRoomList = [...cinemaRoomList]
        if (!newCinemaRoomList.find(value => value.id === -1) && isAdmin) {
            newCinemaRoomList.push({
                id: -1,
            } as CinemaRoom)
            setCinemaRoomListState(newCinemaRoomList)
        }
    }, [cinemaRoomList])
    return (
        <div>
            <header
                className="bg-white space-y-4 p-4 sm:px-8 sm:py-6 lg:p-4 xl:px-8 xl:py-6 flex justify-center font-bold">
                Cinema room list
            </header>
            {cinemaRoomListState.map((value, i, array) =>
                <CinemaRoomListRow key={'cinema_room_list_' + value.id + '_' + i} cinemaRoom={value}
                                   cinemaRoomList={array} index={i}
                                   setCinemaRoomListState={setCinemaRoomListState} cinemaId={cinemaId}
                                   updateData={updateData}/>
            )}
        </div>
    );
};

export default CinemaRoomList;

interface CinemaRoomListRowProps {
    cinemaRoom: CinemaRoom,
    cinemaRoomList: CinemaRoom[],
    index: number,
    setCinemaRoomListState: (cinemaRoomList: CinemaRoom[]) => void,
    updateData: (id: number) => void
    cinemaId: number
}

const CinemaRoomListRow = ({
                               cinemaRoom,
                               cinemaRoomList,
                               setCinemaRoomListState,
                               index,
                               updateData,
                               cinemaId
                           }: CinemaRoomListRowProps) => {
    const [cookies, setCookie, removeCookie] = useCookies(['lvl']);
    const [isAdmin, setIsAdmin] = useState<boolean>(!!cookies.lvl && Number(cookies.lvl) === 100)

    const onChangedInput = (filed: string) => (e: any) => {
        let newItem = {...cinemaRoom}
        newItem[filed] = Number(e.target.value)
        let newCinemaRoomList = [...cinemaRoomList]
        newCinemaRoomList = newCinemaRoomList.map(value => value.id === newItem.id ? newItem : value)
        setCinemaRoomListState(newCinemaRoomList)
    }
    const onDelete = (id: number) => (e: any) => {
        MovieApi.deleteCinemaRoom(id)
            .then(res => {
                updateData(cinemaId)
            })
    }
    const onSave = (id: number, cinemaRoom: CinemaRoom) => (e: any) => {
        cinemaRoom.cinemaId = cinemaId
        MovieApi.saveCinemaRoom(id, cinemaRoom)
            .then(res => {
                updateData(cinemaId)
            })

    }
    return (
        <div className="flex flex-wrap -mx-3 mb-6 w-full p-5">
            <div className="w-full md:w-1/5 px-3 mb-6 md:mb-0">
                <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                       htmlFor="grid-city">
                    Row size
                </label>
                <input
                    className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                    id="grid-city"
                    type="number"
                    value={cinemaRoom.countRow}
                    min={1}
                    disabled={cinemaRoom.id > 0}
                    onChange={onChangedInput('countRow')}>
                </input>
            </div>
            <div className="w-full md:w-1/5 px-3">
                <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                       htmlFor="grid-last-name">
                    Column size
                </label>
                <input
                    className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                    id="grid-last-name"
                    type="number"
                    disabled={cinemaRoom.id > 0}
                    min={1}
                    value={cinemaRoom.countColumn}
                    onChange={onChangedInput('countColumn')}>
                </input>

            </div>
            {cinemaRoom.id > 0 ?
                <>
                    {isAdmin &&
                        <button
                            className={"bg-red-600 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-full md:w-1/5 md:h-1/5 mt-7"}
                            onClick={onDelete(cinemaRoom.id)}>
                            <FontAwesomeIcon icon={faTrashAlt}/>
                        </button>
                    }
                    <Link to={cinemaId + '/session/' + cinemaRoom.id}
                          className="bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded-full md:w-1/5 md:h-1/5 mt-7 ml-5">
                        Sessions
                    </Link>
                </>
                :
                <button
                    className={"bg-blue-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-full md:w-1/5 md:h-1/5 mt-7"}
                    onClick={onSave(cinemaRoom.id, cinemaRoom)}>
                    <FontAwesomeIcon icon={faSave}/>
                </button>
            }

        </div>
    );
};
