DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.columns
    WHERE table_name='login_history' AND column_name='user_id'
  ) THEN
    ALTER TABLE public.login_history ADD COLUMN user_id UUID;
  END IF;
END $$;