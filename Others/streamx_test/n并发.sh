COUNT=400
table_number=8
slice_count=$(echo "($COUNT) / ($table_number)/10" |bc)
echo $slice_count