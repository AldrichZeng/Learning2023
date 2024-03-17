root_dir=`pwd`
echo $root_dir

find "$root_dir" -type d -name "result" -exec rm -rf {} +