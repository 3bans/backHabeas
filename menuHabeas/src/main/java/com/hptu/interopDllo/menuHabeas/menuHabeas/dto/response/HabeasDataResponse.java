package com.hptu.interopDllo.menuHabeas.menuHabeas.dto.response;

import java.time.LocalDate;

public class HabeasDataResponse {

    private Long idRegistro;
    private String noIdentificacion;
    private String tipoId;
    private LocalDate fechaAprobacion;
    private String aprobacion;
    private String idColaborador;
    private String nombreColaborador;
    private String puntoAtencion;
    private String medioAutorizacion;
    private String codigo;
    private String historia;
    private String correoEnviado;
    private String nombreCompletoMedico;
    private String motivoDescripcion;

    // ✅ Constructor requerido por JPQL
    public HabeasDataResponse(Long idRegistro, String noIdentificacion, String tipoId,
                              LocalDate fechaAprobacion, String aprobacion, String idColaborador,
                              String nombreColaborador, String puntoAtencion, String medioAutorizacion,
                              String codigo, String historia, String correoEnviado,
                              String nombreCompletoMedico, String motivoDescripcion) {
        this.idRegistro = idRegistro;
        this.noIdentificacion = noIdentificacion;
        this.tipoId = tipoId;
        this.fechaAprobacion = fechaAprobacion;
        this.aprobacion = aprobacion;
        this.idColaborador = idColaborador;
        this.nombreColaborador = nombreColaborador;
        this.puntoAtencion = puntoAtencion;
        this.medioAutorizacion = medioAutorizacion;
        this.codigo = codigo;
        this.historia = historia;
        this.correoEnviado = correoEnviado;
        this.nombreCompletoMedico = nombreCompletoMedico;
        this.motivoDescripcion = motivoDescripcion;
    }

    // ✅ Getters (puedes generarlos con Lombok o manualmente)
    public Long getIdRegistro() { return idRegistro; }
    public String getNoIdentificacion() { return noIdentificacion; }
    public String getTipoId() { return tipoId; }
    public LocalDate getFechaAprobacion() { return fechaAprobacion; }
    public String getAprobacion() { return aprobacion; }
    public String getIdColaborador() { return idColaborador; }
    public String getNombreColaborador() { return nombreColaborador; }
    public String getPuntoAtencion() { return puntoAtencion; }
    public String getMedioAutorizacion() { return medioAutorizacion; }
    public String getCodigo() { return codigo; }
    public String getHistoria() { return historia; }
    public String getCorreoEnviado() { return correoEnviado; }
    public String getNombreCompletoMedico() { return nombreCompletoMedico; }
    public String getMotivoDescripcion() { return motivoDescripcion; }
}
