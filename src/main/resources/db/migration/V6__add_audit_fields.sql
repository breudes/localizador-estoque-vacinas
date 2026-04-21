-- Add createdBy and updatedBy fields to health_facilities table
ALTER TABLE health_facilities 
ADD COLUMN created_by BIGINT,
ADD COLUMN updated_by BIGINT;

-- Add createdBy and updatedBy fields to vaccines table
ALTER TABLE vaccines 
ADD COLUMN created_by BIGINT,
ADD COLUMN updated_by BIGINT;

-- Add createdBy and updatedBy fields to inventories table
ALTER TABLE inventories 
ADD COLUMN created_by BIGINT,
ADD COLUMN updated_by BIGINT;
