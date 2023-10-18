 var vue=new Vue({
        el: '#app',
        data:{
            keyword:"",
            commodities: [], //查询结果
            currentCommodity:{}, //当前编辑的事项
            dialogVisible: false, //对话框是否显示
            editMode:false  //当前是否是编辑模式（还是添加模式）
        },
        methods: {
            query: function (keyword) {
                //http://localhost:8080/Commodity/1
                var path='/Commodity';
                var self = this
                if(this.keyword!="") {
                    path=path+'/'+this.keyword;
                    axios.get(path)
                        .then(response=>self.commodities = [response.data])
                        .catch(e =>self.$message.error(e.response.data))
                }else{
                    axios.get(path)
                        .then(response=>self.commodities = response.data)
                        .catch(e =>self.$message.error(e.response.data))
                }
            },
            deleteCommodity: function (commodity) {
                var self = this
                axios.delete('/Commodity/'+commodity.id)
                    .then(response=>self.query())
                    .catch(e =>self.$message.error(e.response.data))
            },
            showEdit:function(commodity){
                this.dialogVisible = true
                this.editMode=true;
                this.currentCommodity = Object.assign({},commodity)
            },
            showAdd:function(){
                this.dialogVisible = true
                this.editMode=false;
            },
            saveCommodity:function(){
                var self = this
                if(self.editMode){
                    axios.put('/Commodity'+'?id='+self.currentCommodity.id,self.currentCommodity)
                    .then(response=> self.query())
                    .catch(e =>self.$message.error(e.response.data))
                }else{
                    //console.log(self.currentCommodity);
                    axios.post('/Commodity',self.currentCommodity)
                    .then(response=> self.query())
                    .catch(e => self.$message.error(e.response.data))
                }
                this.dialogVisible = false
            }
        }
    })
    vue.query();