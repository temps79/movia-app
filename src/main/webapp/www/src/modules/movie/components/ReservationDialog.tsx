import React, {useState} from 'react';
import {Button, Dialog, DialogBody, DialogFooter, DialogHeader} from "@material-tailwind/react";
import {Seat, Session} from "../MovieTypes";
import MovieApi from "../api/MovieApi";
import {useHistory} from "react-router-dom";

interface ReservationDialogProps {
    showModal: boolean,
    handleOpen: () => void,
    session: Session,
}

const ReservationDialog = ({showModal, handleOpen, session}: ReservationDialogProps) => {
        let initialSeat = {
            column: 0,
            row: 0
        } as Seat
        const [seat, setSeat] = useState<Seat>(initialSeat)
        const onChangedInput = (filed: string, field2: string) => (e: any) => {
            let newItem = {...seat}
            newItem[filed] = Number(e.target.value)
            if (newItem[filed] <= session.cinemaRoom[field2]) {
                setSeat(newItem)
            }
        }
        const navigate = useHistory();
        const onClick = () => {
            let sendSeat: any = session.seats.find(value => value.row === seat.row && value.column === seat.column)
            MovieApi.updateSeatSession(session.id, sendSeat)
                .then((res:any) => {
                    handleOpen()
                    navigate.push(`/cinema/${res.cinemaRoom.cinemaId}/session/${res.cinemaRoom.id}`)
                })
        }
        return (
            <Dialog open={showModal} handler={handleOpen}>
                <DialogHeader>Reservation</DialogHeader>
                <DialogBody divider>
                    <div>
                        Choose a location!<br/>
                        Count Row:{session.cinemaRoom.countRow}
                        <br/>
                        Count Column:{session.cinemaRoom.countColumn}
                    </div>
                    <div className="flex flex-wrap -mx-3 mb-6">
                        <div className="w-full md:w-1/2 px-3 mb-6 md:mb-0">
                            <div className="w-full md:w-1/2 px-3">
                                <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                                       htmlFor="grid-last-name">
                                    Row
                                </label>
                                <input
                                    id="grid-last-name"
                                    type="number"
                                    min={0}
                                    max={Number(session.cinemaRoom.countRow)}
                                    value={Number(seat.row)}
                                    onChange={onChangedInput('row', 'countRow')}>
                                </input>
                                <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                                       htmlFor="grid-last-name">
                                    Column
                                </label>
                                <input

                                    id="grid-last-name"
                                    type="number"
                                    min={0}
                                    max={Number(session.cinemaRoom.countColumn)}
                                    value={Number(seat.column)}
                                    onChange={onChangedInput('column', 'countColumn')}>
                                </input>
                            </div>
                        </div>
                    </div>
                </DialogBody>
                <DialogFooter>
                    <Button
                        variant="text"
                        color="red"
                        onClick={handleOpen}
                        className="mr-1"
                    >
                        <span>Cancel</span>
                    </Button>
                    <Button variant="gradient" color="green" onClick={onClick}>
                        <span>Confirm</span>
                    </Button>
                </DialogFooter>
            </Dialog>
        );
    }
;

export default ReservationDialog;