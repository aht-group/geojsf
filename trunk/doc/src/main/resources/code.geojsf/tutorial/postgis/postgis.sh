wget http://download.osgeo.org/postgis/source/postgis-2.0.2.tar.gz
tar xfvz postgis-2.0.2.tar.gz
cd postgis-2.0.2

./configure
make
sudo make install
sudo ldconfig
sudo make comments-install