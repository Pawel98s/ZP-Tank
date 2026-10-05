ALTER TABLE waybills
    ALTER COLUMN waybill_number SET NOT NULL;

ALTER TABLE waybills
    ALTER COLUMN issue_date SET NOT NULL;

ALTER TABLE waybills
    ADD CONSTRAINT uk_waybill_number UNIQUE (waybill_number);