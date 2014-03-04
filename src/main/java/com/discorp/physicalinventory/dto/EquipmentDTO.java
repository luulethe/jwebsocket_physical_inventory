package com.discorp.physicalinventory.dto;

/**
 * User: luult
 * Date: 3/4/14
 */
public class EquipmentDTO
{
    Long auditHistoryId;
    Long equipmentWebId;
    String hours;
    String note;
    String markOffReason;
    public Long getAuditHistoryId()
    {
        return auditHistoryId;
    }

    public void setAuditHistoryId(Long auditHistoryId)
    {
        this.auditHistoryId = auditHistoryId;
    }

    public Long getEquipmentWebId()
    {
        return equipmentWebId;
    }

    public void setEquipmentWebId(Long equipmentWebId)
    {
        this.equipmentWebId = equipmentWebId;
    }

    public String getHours()
    {
        return hours;
    }

    public void setHours(String hours)
    {
        this.hours = hours;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public String getMarkOffReason()
    {
        return markOffReason;
    }

    public void setMarkOffReason(String markOffReason)
    {
        this.markOffReason = markOffReason;
    }
}
