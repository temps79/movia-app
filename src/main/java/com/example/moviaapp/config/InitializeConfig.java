package com.example.moviaapp.config;

import com.example.moviaapp.movia.entity.*;
import com.example.moviaapp.movia.service.CinemaRoomFacade;
import com.example.moviaapp.movia.service.CinemaService;
import com.example.moviaapp.movia.service.FilmService;
import com.example.moviaapp.movia.service.SessionFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class InitializeConfig implements CommandLineRunner {
    private final CinemaRoomFacade cinemaRoomFacade;
    private final CinemaService cinemaService;
    private final FilmService filmService;
    private final SessionFacade sessionFacade;

    @Override
    public void run(String... args) throws Exception {
        Cinema cinema = createCinema("Moscow", "Sretensky Blvd., 4, Moscow");
        CinemaRoom cinemaRoom = createCinemaRoom(cinema.getId());
        List<Film> films = createFilms();
        List<Session> sessions = createSession(films, cinemaRoom);
    }

    private Cinema createCinema(String city, String address) {
        Cinema cinema = new Cinema();
        cinema.setCity(city);
        cinema.setAddress(address);
        cinema.setCinemaRoomList(null);
        Cinema saveCinema = cinemaService.save(-1, cinema).getBody();
        return cinemaService.findById(saveCinema.getId()).getBody();
    }

    private CinemaRoom createCinemaRoom(int cinemaId) {
        CinemaRoom cinemaRoom = new CinemaRoom();
        cinemaRoom.setCountColumn(6);
        cinemaRoom.setCountRow(5);
        cinemaRoom.setCinema(cinemaId);
        return cinemaRoomFacade.save(cinemaRoom).getBody();
    }

    private List<Film> createFilms() {
        return Stream.of(
                new Film(-1,
                        "Операция «Фортуна»: Искусство побеждать",
                        true,
                        "Элитный разведчик Орсон Форчун получил новое задание: остановить продажу и распространение смертельного оружия. Но в одиночку ему эту миссию не потянуть.",
                        "https://kinopoisk-ru.clstorage.net/w2Ro89453/aa53cfqZ9/HtQ9fuYgmm_7u0UeM1F_d46kz6gXzxPdUQvXfhMO4M80J6Pkk9zTkOW1PJaH-UBWE55Fl-mTRIQ8rgSqDqeLSC_cPG-rXN4b36Y9Zl4AFgbDjL7MBtNq_AgbozNmY5xz9IdrQVtELzBlBLmbluU1I3bjRIPdsw3NFYJJYuujU31G7BGeLnBT_6cvbOKbXf-rXmkf2ItSM82B9ltiA4bIByizi4F_pNtU5cSdXw6U7a9y55xPub8JuEnOLVIKP8NZ-kiZqtpUl-dLZ1xqB9mjjzJdQxQn0rNdXTL_ki-mdO-M26sdMxyzqPWQ5Rt6EJHLYvu0Ewl3qYgRFjEigm-DifJoKbZ--INPExMsOpMd5z-ChWswD0YX1X2Sw2o7krwTBJ-3AXNI27hJFKUTIjBll2rLJJOpn03clf5Rkv5rg-E2PNluJjRvly9vIOpLQVd_tkFLrKNWt8UF8vt66wo8G5Sra42bLDPEQTTpZxKQqU9-95h_QVPNRIXaCbbeS0PNQqh9GsqIH5_H81A6E9HXd94tk4iH9peVHcaLWhNeQD_UC3fx-6DTTGVotfP6mNEzcg9oB83LlTwl4i0qHmvbDYKcWf5y4D-PK_tQIseF76cCQUcMv67Pxf1-Z7rTfgjrFBdbecM8__zd9GVXHnitA8K_sCclz_XAgVIVxg6fgymyCBW-ptx7k_ufUALnTd8DspG7jFuWt_WNQrPeMwoIg0i_uwkjfDdYwYhhn3aIQZMa0xifTc8ZQHluTTKit5cJyiQFts60y0snB8jC89HnU3r1x9yXwnPdnRrXuosOuFe4a68BL1wnTCUESQ_qkKUDzgNcJ7mTZdDJzqWC3qcLcfqAoTrmyMdTq8NoGhNRW0u6Cc9cu063bV3-S94jfkgnFFejWUN0u0BdpLWX6mQVB3LTWBNBN334hRpVIoaTy10WSJnOdvyXk--7WLIHTcO3lhEzFAtac33tQgu6rx48H9TDd9Fj4MNQTYhN86L82ZcaQyijNcd1xMmWXVKuvz_hHqypOuZwT7_jv6z6Yw1zD_7B_7jbVvfp_UrnHn-6dMMMO1NlDzQDfFEkDRMq5MVXMtNU213LjSwJYlGqdpez_SY4iUYqTJMzLwNY9gNBHy_eSdvAj_pz-VFGb3a_smS76Gennf_wh1ThZMEvYowxBxZDINexjwmARR7ZIpKXE8XCWAm6uoxH41PrFL5bbd_3UgkvFOfSZxnt7uvmayrUR1zfw9UXJE8A2eCx-x4YySu2v3wDIVuVMHEqsTbCH3_NvhQRov4gRwtTa6wuk92Dd1Yda1jXSnMdZQrrWutuVJO8z6fFZ8BHfO38xacqDIFrYsc0e6UfqTh5hs1-2ifHCQbEIWYqjPNr80OAMo9F_-9eITeMw0qb6YlKC_KblkinwEuv1RNMRzRdbLULboAZx4KPEONJO5HUmQZRhl4fqx1OPCWCTtALY1OP1BKDdQN32u3vzE_et40t6ms2Iy4QU4zHU3GDXCtIfXQ9466YWYOye-TbWaOF3BFG3TpW77cFpqQZEna4s4-Lr7y6U11j46adPxjXrpNxyT53ko9W5O-UR8OZ42BPLMVETadq4NmfAlN8R30H2dgNQl1W_ufHWcbkDRoCAG_bi5MMLkv930te8e-0T8aHidGC2zqrnkAraOvPlXd0D7xJJBGXqoQtu86bwHMtx1k09e6VVrIXH1XWTBEeprAT549jSLpvcR8nQln7cMvSD6GBkkdipx6EIxgH-42XXKPUpbQVC-JQGevuMyjjLf9Z-HV-CYbem9PhTri9giJE59OfQ1yS750bM1ZB56AnohPtzZY_7vfqVINs36uhQ1A3PM0wNRMCkAUf8n8ce1HDhcQhTpUOTkNXGdbA9fr2OM8Hg4dYfgd5y8s6eVfEp6p7AQlqe2bfXhwzqFtHAZdE_wDZjBlLQoCZq5bHUFv5V03QYQY5Fgrzr-UOQGFGzmRnk8uXvMLfbf9HMplrMGe-E1mF5v-WU5aIaxw3X6HPqP-ASWDRLx4g7bPCj8xfgb99bBFqXb6KC6MZSrj5BqooB2tnI0Ca07WvQ57tPyzXpr8l0WLLegPuHGfo1zP58-RTnHU0Nd8WPGkXljNgk0EjVdS1hsHC8hdXHTJIdSb6rItHbz-45t-Ji6NW6Ws0I7L7Ae1SO94DrtSLqGu3ZZ_oX8CtvOkbziwVh0KP3J_dIwU8qYLV_lqfy-XWVPn6fsjPa4OL4L5nSZuvchUz8B8Kz2WVGofy77LwW5Rnf5mHQCe0LYDlV9JwiROeh_xfJXOBeMnWvRbWR495XtRxMsrAF2uL5-xGwxGHt9bhtwCvxr-FXRpTEjOGyEM0k8stsyBvgBF4QU_60E2r5sPEp-kvdUQ1EgUmAiffgRaAMW76rAMHy_fAIpMd61uyofsIh0K33WEO-157KpTPPFtHEWPwSwyx8DWnDozZG8Jr7F9JG8WECdL1IgoPg-Ui5I2a0mxPB5PLVBIHYYNrRllL3Nt26ykh_n8y02qcS9y_Q0WryOuctSiN407cKQuKbyxb0V-N_J3SfSIuN1OJUhCx6iok6_vDv8TGk_3_s9JZv9DDWjdxEYaLMv-OYGvYa3vh9-h_nGF0DXuGWKk74s-wSwHfITRhnpmiIo-7eaqcNW4uoOv_h0ssduMdW3dyQTOkJ0rL9XlWO1Yn_pDP6OvDqa9sJwSx9LHfojzJixZH8PtNXwnUpcaRDqqfp9UKMHnO1kDzzxubXGbP5YOHkv3ruB_-G9HVRt-O3644x7i_h30zuEcoRby1H1JwCZv-M9gfoR91dIkKMZIyt3v9rsAd_rJkm8vH79wOexn3x6bxS_gXJhPpyWL3Bh9uBCs8hzdpq5yjzOnI2dP6mOUT4p_kD_FzzcCpznnWvoMHYVqkATJyVLN7W-PIZt_R-98iDVuEE1KPdeF2X3pzZpCLTJczVSP8g7ChZKH7cqjBm8pX0JNNI5XQGfI8",
                        114
                ),
                new Film(-1,
                        "Быстрее пули",
                        true,
                        "Наёмник под кодовым именем Божья Коровка отправляется на новую миссию: вместо заболевшего коллеги он должен сесть в скоростной поезд Токио — Киото, выкрасть чемоданчик и сойти на промежуточной станции.",
                        "https://kinopoisk-ru.clstorage.net/w2Ro89453/aa53cfqZ9/HtQ9fuYgmm_7u0UeM1F_d46kz6gXzxPdUQvXfhMO4M80J6Pkk9zTkOW1PJaH-UBWE55FmrTXWLl530yrT_bWJC_cKGunXbISnu48IzIwJirTjLbwG49i_AgbozNmY5xz9IdrQVtELzBlBLmbluU1I3bjRIPdsw3NFYJJYuujU31G7BGeLnBT_6cvbOKbXf-rXmkf2ItSM82B9ltiA4bIByizi4F_pNtU5cSdXw6U7a9y55xPub8JuEnOLVIKP8NZ-kiZqtpUl-dLZ1xqB9mjjzJdQxQn0rNdXTL_ki-mdO-M26sdMxyzqPWQ5Rt6EJHLYvu0Ewl3qYgRFjEigm-DifJoKbZ--INPExMsOpMd5z-ChWswD0YX1X2Sw2o7krwTBJ-3AXNI27hJFKUTIjBll2rLJJOpn03clf5Rkv5rg-E2PNluJjRvly9vIOpLQVd_tkFLrKNWt8UF8vt66wo8G5Sra42bLDPEQTTpZxKQqU9-95h_QVPNRIXaCbbeS0PNQqh9GsqIH5_H81A6E9HXd94tk4iH9peVHcaLWhNeQD_UC3fx-6DTTGVotfP6mNEzcg9oB83LlTwl4i0qHmvbDYKcWf5y4D-PK_tQIseF76cCQUcMv67Pxf1-Z7rTfgjrFBdbecM8__zd9GVXHnitA8K_sCclz_XAgVIVxg6fgymyCBW-ptx7k_ufUALnTd8DspG7jFuWt_WNQrPeMwoIg0i_uwkjfDdYwYhhn3aIQZMa0xifTc8ZQHluTTKit5cJyiQFts60y0snB8jC89HnU3r1x9yXwnPdnRrXuosOuFe4a68BL1wnTCUESQ_qkKUDzgNcJ7mTZdDJzqWC3qcLcfqAoTrmyMdTq8NoGhNRW0u6Cc9cu063bV3-S94jfkgnFFejWUN0u0BdpLWX6mQVB3LTWBNBN334hRpVIoaTy10WSJnOdvyXk--7WLIHTcO3lhEzFAtac33tQgu6rx48H9TDd9Fj4MNQTYhN86L82ZcaQyijNcd1xMmWXVKuvz_hHqypOuZwT7_jv6z6Yw1zD_7B_7jbVvfp_UrnHn-6dMMMO1NlDzQDfFEkDRMq5MVXMtNU213LjSwJYlGqdpez_SY4iUYqTJMzLwNY9gNBHy_eSdvAj_pz-VFGb3a_smS76Gennf_wh1ThZMEvYowxBxZDINexjwmARR7ZIpKXE8XCWAm6uoxH41PrFL5bbd_3UgkvFOfSZxnt7uvmayrUR1zfw9UXJE8A2eCx-x4YySu2v3wDIVuVMHEqsTbCH3_NvhQRov4gRwtTa6wuk92Dd1Yda1jXSnMdZQrrWutuVJO8z6fFZ8BHfO38xacqDIFrYsc0e6UfqTh5hs1-2ifHCQbEIWYqjPNr80OAMo9F_-9eITeMw0qb6YlKC_KblkinwEuv1RNMRzRdbLULboAZx4KPEONJO5HUmQZRhl4fqx1OPCWCTtALY1OP1BKDdQN32u3vzE_et40t6ms2Iy4QU4zHU3GDXCtIfXQ9466YWYOye-TbWaOF3BFG3TpW77cFpqQZEna4s4-Lr7y6U11j46adPxjXrpNxyT53ko9W5O-UR8OZ42BPLMVETadq4NmfAlN8R30H2dgNQl1W_ufHWcbkDRoCAG_bi5MMLkv930te8e-0T8aHidGC2zqrnkAraOvPlXd0D7xJJBGXqoQtu86bwHMtx1k09e6VVrIXH1XWTBEeprAT549jSLpvcR8nQln7cMvSD6GBkkdipx6EIxgH-42XXKPUpbQVC-JQGevuMyjjLf9Z-HV-CYbem9PhTri9giJE59OfQ1yS750bM1ZB56AnohPtzZY_7vfqVINs36uhQ1A3PM0wNRMCkAUf8n8ce1HDhcQhTpUOTkNXGdbA9fr2OM8Hg4dYfgd5y8s6eVfEp6p7AQlqe2bfXhwzqFtHAZdE_wDZjBlLQoCZq5bHUFv5V03QYQY5Fgrzr-UOQGFGzmRnk8uXvMLfbf9HMplrMGe-E1mF5v-WU5aIaxw3X6HPqP-ASWDRLx4g7bPCj8xfgb99bBFqXb6KC6MZSrj5BqooB2tnI0Ca07WvQ57tPyzXpr8l0WLLegPuHGfo1zP58-RTnHU0Nd8WPGkXljNgk0EjVdS1hsHC8hdXHTJIdSb6rItHbz-45t-Ji6NW6Ws0I7L7Ae1SO94DrtSLqGu3ZZ_oX8CtvOkbziwVh0KP3J_dIwU8qYLV_lqfy-XWVPn6fsjPa4OL4L5nSZuvchUz8B8Kz2WVGofy77LwW5Rnf5mHQCe0LYDlV9JwiROeh_xfJXOBeMnWvRbWR495XtRxMsrAF2uL5-xGwxGHt9bhtwCvxr-FXRpTEjOGyEM0k8stsyBvgBF4QU_60E2r5sPEp-kvdUQ1EgUmAiffgRaAMW76rAMHy_fAIpMd61uyofsIh0K33WEO-157KpTPPFtHEWPwSwyx8DWnDozZG8Jr7F9JG8WECdL1IgoPg-Ui5I2a0mxPB5PLVBIHYYNrRllL3Nt26ykh_n8y02qcS9y_Q0WryOuctSiN407cKQuKbyxb0V-N_J3SfSIuN1OJUhCx6iok6_vDv8TGk_3_s9JZv9DDWjdxEYaLMv-OYGvYa3vh9-h_nGF0DXuGWKk74s-wSwHfITRhnpmiIo-7eaqcNW4uoOv_h0ssduMdW3dyQTOkJ0rL9XlWO1Yn_pDP6OvDqa9sJwSx9LHfojzJixZH8PtNXwnUpcaRDqqfp9UKMHnO1kDzzxubXGbP5YOHkv3ruB_-G9HVRt-O3644x7i_h30zuEcoRby1H1JwCZv-M9gfoR91dIkKMZIyt3v9rsAd_rJkm8vH79wOexn3x6bxS_gXJhPpyWL3Bh9uBCs8hzdpq5yjzOnI2dP6mOUT4p_kD_FzzcCpznnWvoMHYVqkATJyVLN7W-PIZt_R-98iDVuEE1KPdeF2X3pzZpCLTJczVSP8g7ChZKH7cqjBm8pX0JNNI5XQGfI8",
                        126
                )
        ).map(film -> filmService.save(-1, film).getBody()).collect(Collectors.toList());
    }

    private List<Session> createSession(List<Film> films, CinemaRoom cinemaRoom) {
        Random random = new Random();
        return films
                .stream().map(film -> sessionFacade.save(new Session(-1, film, cinemaRoom, LocalDateTime.of(2022, 12, 4, random.nextInt(10), random.nextInt(60)), random.nextInt(1000), Collections.emptySet())).getBody())
                .collect(Collectors.toList());
    }
}
