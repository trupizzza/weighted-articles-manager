SELECT MATNO,
  MATNAM,
  MATDSC,
  PRICE,
  HASTOBEWEIGHED,
  SCALEPLU,
  EXPDATSHOP,
  LIFE_BOX_TOTAL,
  SCALEEXPIRYDAYS,
  CASE
    WHEN HASTOBEWEIGHED='Y'
    THEN
      CASE
        WHEN EXPDATSHOP2 IS NULL
        OR EXPDATE       IS NULL
        THEN NULL
        WHEN TO_DATE(EXPDATSHOP2, 'DD.MM.YYYY') - TO_DATE(EXPDATE, 'DD.MM.YYYY')>=0
        THEN EXPDATE
        ELSE EXPDATSHOP2
      END
    ELSE EXPDATSHOP
  END EXPDATE,
  LABEL,
  GID,
  ADDNO1,
  MOREPRICES,
  CASE
    WHEN HASTOBEWEIGHED='Y'
    THEN
      CASE
        WHEN TO_DATE(EXPDATSHOP2, 'DD.MM.YYYY') - TO_DATE(EXPDATE, 'DD.MM.YYYY')>=0
        THEN
          CASE
            WHEN TO_DATE(EXPDATE, 'DD.MM.YYYY') - TO_DATE(TO_CHAR(SYSDATE, 'dd.mm.yyyy'))>=0
            THEN TO_DATE(EXPDATE, 'DD.MM.YYYY') - TO_DATE(TO_CHAR(SYSDATE, 'dd.mm.yyyy'))
            ELSE 364
          END
        ELSE
          CASE
            WHEN TO_DATE(EXPDATSHOP2, 'DD.MM.YYYY') - TO_DATE(TO_CHAR(SYSDATE, 'dd.mm.yyyy'))>=0
            THEN TO_DATE(EXPDATSHOP2, 'DD.MM.YYYY') - TO_DATE(TO_CHAR(SYSDATE, 'dd.mm.yyyy'))
            ELSE 364
          END
      END
    ELSE
      CASE
        WHEN TO_DATE(EXPDATSHOP, 'DD.MM.YYYY') - TO_DATE(TO_CHAR(SYSDATE, 'dd.mm.yyyy'))>=0
        THEN TO_DATE(EXPDATSHOP, 'DD.MM.YYYY') - TO_DATE(TO_CHAR(SYSDATE, 'dd.mm.yyyy'))
        ELSE 364
      END
  END EXPDAYS2SCALE,
  EXPDATSHOP2
FROM
  (SELECT MATNO,
    MATNAM,
    MATDSC,
    PRICE,
    HASTOBEWEIGHED,
    SCALEPLU,
    EXPDATSHOP,
    EXPDATSHOP2,
    LIFE_BOX_TOTAL,
    SCALEEXPIRYDAYS,
    CASE
      WHEN TO_DATE(EXPDATE1,'dd.mm.yyyy')>TO_DATE(EXPDATE2,'dd.mm.yyyy')
      THEN EXPDATE2
      WHEN EXPDATE2 IS NULL
      THEN NULL
      ELSE EXPDATE1
    END EXPDATE,
    LABEL,
    GID,
    ADDNO1,
    MOREPRICES
  FROM
    (SELECT MATNO,
      MATNAM,
      MATDSC,
      PRICE,
      HASTOBEWEIGHED,
      SCALEPLU,
      TO_CHAR(EXPDATSHOP,'dd.mm.yyyy') EXPDATSHOP,
      TO_CHAR(EXPDATSHOP2,'dd.mm.yyyy') EXPDATSHOP2,
      CASE
          (SELECT TYPE FROM MATSIGNGRP WHERE SGCODE = 'LBT' AND STATUS='A'
          )
        WHEN 'L'
        THEN
          (SELECT MS.SNAME
          FROM MAT2SIGN M2S,
            MATSIGNGRP MSG,
            MATSIGN MS
          WHERE M2S.SGRPID = MSG.GID
          AND MSG.SGCODE   = 'LBT'
          AND M2S.MATID    = MAT.GID
          AND MS.GID       = M2S.SIGNID
          AND MSG.STATUS   = 'A'
          AND M2S.STATUS   = 'A'
          AND MS.STATUS    ='A'
          )
        WHEN 'N'
        THEN
          (SELECT TO_CHAR(M2S.NVAL)
          FROM MAT2SIGN M2S,
            MATSIGNGRP MSG
          WHERE M2S.SGRPID = MSG.GID
          AND MSG.SGCODE   = 'LBT'
          AND M2S.MATID    = MAT.GID
          AND MSG.STATUS   = 'A'
          AND M2S.STATUS   = 'A'
          )
        ELSE
          (SELECT M2S.SVAL
          FROM MAT2SIGN M2S,
            MATSIGNGRP MSG
          WHERE M2S.SGRPID = MSG.GID
          AND MSG.SGCODE   = 'LBT'
          AND M2S.MATID    = MAT.GID
          AND MSG.STATUS   = 'A'
          AND M2S.STATUS   = 'A'
          )
      END LIFE_BOX_TOTAL,
      SCALEEXPIRYDAYS,
      DECODE(TO_CHAR(EXPDATSHOP, 'dd.mm.yyyy'), NULL, NULL, TO_CHAR(EXPDATSHOP+DECODE((
      CASE
          (SELECT TYPE FROM MATSIGNGRP WHERE SGCODE = 'LBT' AND STATUS='A'
          )
        WHEN 'L'
        THEN
          (SELECT MS.SNAME
          FROM MAT2SIGN M2S,
            MATSIGNGRP MSG,
            MATSIGN MS
          WHERE M2S.SGRPID = MSG.GID
          AND MSG.SGCODE   = 'LBT'
          AND M2S.MATID    = MAT.GID
          AND MS.GID       = M2S.SIGNID
          AND MSG.STATUS   = 'A'
          AND M2S.STATUS   = 'A'
          AND MS.STATUS    ='A'
          )
        WHEN 'N'
        THEN
          (SELECT TO_CHAR(M2S.NVAL)
          FROM MAT2SIGN M2S,
            MATSIGNGRP MSG
          WHERE M2S.SGRPID = MSG.GID
          AND MSG.SGCODE   = 'LBT'
          AND M2S.MATID    = MAT.GID
          AND MSG.STATUS   = 'A'
          AND M2S.STATUS   = 'A'
          )
        ELSE
          (SELECT M2S.SVAL
          FROM MAT2SIGN M2S,
            MATSIGNGRP MSG
          WHERE M2S.SGRPID = MSG.GID
          AND MSG.SGCODE   = 'LBT'
          AND M2S.MATID    = MAT.GID
          AND MSG.STATUS   = 'A'
          AND M2S.STATUS   = 'A'
          )
      END ), NULL, NULL, (
      CASE
          (SELECT TYPE FROM MATSIGNGRP WHERE SGCODE = 'LBT' AND STATUS='A'
          )
        WHEN 'L'
        THEN
          (SELECT MS.SNAME
          FROM MAT2SIGN M2S,
            MATSIGNGRP MSG,
            MATSIGN MS
          WHERE M2S.SGRPID = MSG.GID
          AND MSG.SGCODE   = 'LBT'
          AND M2S.MATID    = MAT.GID
          AND MS.GID       = M2S.SIGNID
          AND MSG.STATUS   = 'A'
          AND M2S.STATUS   = 'A'
          AND MS.STATUS    ='A'
          )
        WHEN 'N'
        THEN
          (SELECT TO_CHAR(M2S.NVAL)
          FROM MAT2SIGN M2S,
            MATSIGNGRP MSG
          WHERE M2S.SGRPID = MSG.GID
          AND MSG.SGCODE   = 'LBT'
          AND M2S.MATID    = MAT.GID
          AND MSG.STATUS   = 'A'
          AND M2S.STATUS   = 'A'
          )
        ELSE
          (SELECT M2S.SVAL
          FROM MAT2SIGN M2S,
            MATSIGNGRP MSG
          WHERE M2S.SGRPID = MSG.GID
          AND MSG.SGCODE   = 'LBT'
          AND M2S.MATID    = MAT.GID
          AND MSG.STATUS   = 'A'
          AND M2S.STATUS   = 'A'
          )
      END )), 'dd.mm.yyyy')) EXPDATE1,
      DECODE( TO_CHAR(SYSDATE + SCALEEXPIRYDAYS, 'dd.mm.yyyy'), TO_CHAR(SYSDATE, 'dd.mm.yyyy'), NULL, TO_CHAR(SYSDATE + SCALEEXPIRYDAYS, 'dd.mm.yyyy')) EXPDATE2,
      CASE
          (SELECT MS.SNAME
          FROM MAT2SIGN M2S,
            MATSIGNGRP MSG,
            MATSIGN MS
          WHERE M2S.SGRPID = MSG.GID
          AND MSG.SGCODE   = 'SERT'
          AND M2S.MATID    = MAT.GID
          AND MS.GID       = M2S.SIGNID
          AND M2S.STATUS   = 'A'
          )
        WHEN 'PCT'
        THEN '6'
        WHEN 'EAC'
        THEN '4'
        ELSE '4'
      END LABEL,
      MAT.GID,
      WG.ADDNO1,
      MAT.MOREPRICES
    FROM MATERIAL MAT
    LEFT JOIN WAREGROUP WG
    ON MAT.WGID         = WG.GID
    WHERE MAT.SCALEPLU IS NOT NULL
    AND MAT.STATUS      = 'A'
    AND MAT.DNYUSE      = 'N'