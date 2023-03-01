import streamlit as st
import pandas as pd
import numpy as np
from PIL import Image
import datetime
import time

st.title("Streamlit")
ankieta, staty = st.tabs(["Ankieta", "Staty"])

with ankieta:
    st.header("Ankieta")
    firstname = st.text_input("Imię", "Wpisz tutaj...")
    lastname = st.text_input("Nazwisko", "Wpisz tutaj...")
    if st.button("Wyślij"):
        st.success("Wysłano formularz")

with staty:
    st.header("Staty")
    data = st.file_uploader("Wyślij swoje dane", type=['csv'])
    if data is not None:
        with st.spinner("Proszę czekać..."):
            time.sleep(1) #Małe datasety, dlatego time sleep, aby można było zobaczyć spinner
            df = pd.read_csv(data, sep=',')
            st.dataframe(df.head(10))
        st.success('Dane zostały wysłane!')
    if data is not None:
        st.header('Visualize your data')
        graph_type = st.radio('Wybierz typ grafu', ['bar chart', 'line chart'])
        st.set_option('deprecation.showPyplotGlobalUse', False)
        all_columns_names = df.columns.to_list()
        selected_column_names = st.multiselect("Select columns to plot", all_columns_names)
        plot_data = df[selected_column_names]
        if st.button('Wyświetl'):

            if graph_type == 'bar chart':
                st.bar_chart(plot_data)
            if graph_type == 'line chart':
                st.line_chart(plot_data)