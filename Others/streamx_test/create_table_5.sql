drop table if exists public.test_performance_wide_5; 
create table public.test_performance_wide_5(    id serial primary key,
    bigint_col bigint,
    timestamp_col timestamp,
    time_col time,
    date_col date,
    bool_col boolean,
    column1 varchar(10),
    column2 varchar(10),
    column3 varchar(10),
    column4 varchar(10),
    column5 varchar(10),
    column6 varchar(10),
    column7 varchar(10),
    column8 varchar(10),
    column9 varchar(10),
    column10 varchar(10),
    column11 varchar(10),
    column12 varchar(10),
    column13 varchar(10),
    column14 varchar(10),
    column15 varchar(10),
    column16 varchar(10),
    column17 varchar(10),
    column18 varchar(10),
    column19 varchar(10),
    column20 varchar(10),
    column21 varchar(10),
    column22 varchar(10),
    column23 varchar(10),
    column24 varchar(10),
    column25 varchar(10),
    column26 varchar(10),
    column27 varchar(10),
    column28 varchar(10),
    column29 varchar(10),
    column30 varchar(10),
    column31 varchar(10),
    column32 varchar(10),
    column33 varchar(10),
    column34 varchar(10),
    column35 varchar(10),
    column36 varchar(10),
    column37 varchar(10),
    column38 varchar(10),
    column39 varchar(10),
    column40 varchar(10),
    column41 varchar(10),
    column42 varchar(10),
    column43 varchar(10),
    column44 varchar(10),
    column45 varchar(10),
    column46 varchar(10),
    column47 varchar(10),
    column48 varchar(10),
    column49 varchar(10),
    column50 varchar(10),
    column51 varchar(10),
    column52 varchar(10),
    column53 varchar(10),
    column54 varchar(10),
    column55 varchar(10),
    column56 varchar(10),
    column57 varchar(10),
    column58 varchar(10),
    column59 varchar(10),
    column60 varchar(10),
    column61 varchar(10),
    column62 varchar(10),
    column63 varchar(10),
    column64 varchar(10),
    column65 varchar(10),
    column66 varchar(10),
    column67 varchar(10),
    column68 varchar(10),
    column69 varchar(10),
    column70 varchar(10),
    column71 varchar(10),
    column72 varchar(10),
    column73 varchar(10),
    column74 varchar(10),
    column75 varchar(10),
    column76 varchar(10),
    column77 varchar(10),
    column78 varchar(10),
    column79 varchar(10),
    column80 varchar(10),
    column81 varchar(10),
    column82 varchar(10),
    column83 varchar(10),
    column84 varchar(10),
    column85 varchar(10),
    column86 varchar(10),
    column87 varchar(10),
    column88 varchar(10),
    column89 varchar(10),
    column90 varchar(10),
    column91 varchar(10),
    column92 varchar(10),
    column93 varchar(10),
    column94 varchar(10),
    column95 varchar(10),
    column96 varchar(10),
    column97 varchar(10),
    column98 varchar(10),
    column99 varchar(10),
    column100 varchar(10),
    column101 varchar(10),
    column102 varchar(10),
    column103 varchar(10),
    column104 varchar(10),
    column105 varchar(10),
    column106 varchar(10),
    column107 varchar(10),
    column108 varchar(10),
    column109 varchar(10),
    column110 varchar(10),
    column111 varchar(10),
    column112 varchar(10),
    column113 varchar(10),
    column114 varchar(10),
    column115 varchar(10),
    column116 varchar(10),
    column117 varchar(10),
    column118 varchar(10),
    column119 varchar(10),
    column120 varchar(10),
    column121 varchar(10),
    column122 varchar(10),
    column123 varchar(10),
    column124 varchar(10),
    column125 varchar(10),
    column126 varchar(10),
    column127 varchar(10),
    column128 varchar(10),
    column129 varchar(10),
    column130 varchar(10),
    column131 varchar(10),
    column132 varchar(10),
    column133 varchar(10),
    column134 varchar(10),
    column135 varchar(10),
    column136 varchar(10),
    column137 varchar(10),
    column138 varchar(10),
    column139 varchar(10),
    column140 varchar(10),
    column141 varchar(10),
    column142 varchar(10),
    column143 varchar(10),
    column144 varchar(10),
    column145 varchar(10),
    column146 varchar(10),
    column147 varchar(10),
    column148 varchar(10),
    column149 varchar(10),
    column150 varchar(10),
    column151 varchar(10),
    column152 varchar(10),
    column153 varchar(10),
    column154 varchar(10),
    column155 varchar(10),
    column156 varchar(10),
    column157 varchar(10),
    column158 varchar(10),
    column159 varchar(10),
    column160 varchar(10),
    column161 varchar(10),
    column162 varchar(10),
    column163 varchar(10),
    column164 varchar(10),
    column165 varchar(10),
    column166 varchar(10),
    column167 varchar(10),
    column168 varchar(10),
    column169 varchar(10),
    column170 varchar(10),
    column171 varchar(32),
    column172 varchar(32),
    column173 varchar(32),
    column174 varchar(32),
    column175 varchar(32),
    column176 varchar(32),
    column177 varchar(32),
    column178 varchar(32),
    column179 varchar(32),
    column180 varchar(32),
    column181 varchar(64),
    column182 varchar(64),
    column183 varchar(64),
    column184 varchar(64),
    column185 varchar(64),
    column186 varchar(64),
    column187 varchar(64),
    column188 varchar(64),
    column189 varchar(64),
    column190 varchar(64),
    column191 varchar(128),
    column192 varchar(128),
    column193 varchar(128),
    column194 varchar(128),
    column195 varchar(128),
    column196 varchar(128),
    column197 varchar(128),
    column198 varchar(128),
    column199 varchar(128),
    column200 varchar(128))