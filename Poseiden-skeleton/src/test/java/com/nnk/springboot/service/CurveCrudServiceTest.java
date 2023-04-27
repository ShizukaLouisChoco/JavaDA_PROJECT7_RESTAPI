package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.Impl.CurveCrudServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class CurveCrudServiceTest {
    @Mock
    CurvePointRepository curvePointRepository;

    @InjectMocks
    CurveCrudServiceImpl curveCrudService;

    @Test
    @DisplayName("curveService calls curveRepository create method")
    public void given_whenCreateCurve_thenReturnCurvePoint() {
        //GIVEN
        CurvePoint curvePoint = new CurvePoint(1,1.,1.);
        //WHEN
        curveCrudService.create(curvePoint);
        //THEN
        ArgumentCaptor<CurvePoint> curvePointArgumentCaptor = ArgumentCaptor.forClass(CurvePoint.class);
        verify(curvePointRepository, times(1)).save(curvePointArgumentCaptor.capture());

        assertThat(curvePointArgumentCaptor.getValue())
                .isNotNull()
                .satisfies(arg -> {
                    assertThat(arg.getId()).isEqualTo(curvePoint.getId());
                });

    }
}
