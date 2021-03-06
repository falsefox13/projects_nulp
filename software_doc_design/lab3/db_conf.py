from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker, scoped_session


conn_str = 'mssql+pyodbc://DESKTOP-OUSJMT4\\MSSQLSERVER01/AjaxSystems?' \
           'trusted_connection=yes;driver=ODBC+Driver+17+for+SQL+Server'
engine = create_engine(conn_str)
session = scoped_session(sessionmaker(bind=engine))

Base = declarative_base()
Base.metadata.create_all(engine)
Base.query = session.query_property()
